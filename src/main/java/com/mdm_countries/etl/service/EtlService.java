package com.mdm_countries.etl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdm_countries.etl.dto.MdmCountryDTO;
import com.mdm_countries.etl.mapper.EtlToMdmTransformer;
import com.mdm_countries.etl.model.Country;
import com.mdm_countries.etl.model.Download;
import com.mdm_countries.etl.model.Provider;
import com.mdm_countries.etl.repository.DownloadRepository;
import com.mdm_countries.etl.repository.ProviderRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EtlService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final DownloadRepository downloadRepository;
    private final ProviderRepository providerRepository;

    private static final String API_URL = "https://restcountries.com/v3.1/all";
    private static final String RAW_DIR = "raw_data/";
    private static final String RAW_DIR_FULL = "raw_data_full/";

    public EtlService(DownloadRepository downloadRepository, ProviderRepository providerRepository) {
        this.downloadRepository = downloadRepository;
        this.providerRepository = providerRepository;
    }

    public String downloadAndStoreJsonChunks(Long providerId) throws IOException {
        Provider provider = providerRepository.findById(providerId)
            .orElseThrow(() -> new IllegalArgumentException("Provider not found with ID: " + providerId));

        String timestamp = java.time.LocalDateTime.now()
            .toString()
            .replace(":", "-")
            .replace(".", "-");

        String rawFileName = "countries_raw_" + provider.getProviderId() + "_" + timestamp + ".json";
        
        Files.createDirectories(Paths.get(RAW_DIR_FULL));
        Files.createDirectories(Paths.get(RAW_DIR));

        String jsonString = restTemplate.getForObject(provider.getProviderUrl(), String.class);

        Path rawFilePath = Paths.get(RAW_DIR_FULL + rawFileName);
        Files.writeString(rawFilePath, jsonString);

        
        // Registra o download com nome do arquivo único
        Download download = new Download();
        download.setProvider(provider);
        download.setRawFilePath(rawFilePath.toString());
        download.setDownloadedAt(LocalDateTime.now());
        downloadRepository.save(download);

        List<Country> countries = objectMapper.readValue(jsonString, new TypeReference<>() {});

        int chunkSize = 50;
        int total = countries.size();
        int totalChunks = (int) Math.ceil((double) total / chunkSize);

        for (int i = 0; i < totalChunks; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, total);
            List<Country> chunk = countries.subList(start, end);

            File file = new File(RAW_DIR + "countries_chunk_" + provider.getProviderId() + "_" + (i + 1) + ".json");
            objectMapper.writeValue(file, chunk);
        }

        return "Total de países: " + total + ". Arquivos gerados: " + totalChunks;
    }


    public String loadAllJsonToMdm(Long downloadId, String mdmUrl) throws IOException {
        Download download = downloadRepository.findById(downloadId)
            .orElseThrow(() -> new IllegalArgumentException("Download not found with ID: " + downloadId));

        File rawFile = new File(download.getRawFilePath());

        int totalEnviados = 0;

        List<Country> countries = objectMapper.readValue(rawFile, new TypeReference<>() {});
        for (Country etlCountry : countries) {
            totalEnviados++;
            MdmCountryDTO dto = EtlToMdmTransformer.transform(etlCountry);
            dto.setCountryId(totalEnviados);
            restTemplate.postForEntity(mdmUrl, dto, String.class);
        }

        return "Total de países enviados ao MDM: " + totalEnviados;
    }

    public List<Download> getAllDownloads() {
        return downloadRepository.findAll();
    }
}
