package com.mdm_countries.etl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdm_countries.etl.dto.MdmCountryDTO;
import com.mdm_countries.etl.mapper.EtlToMdmTransformer;
import com.mdm_countries.etl.model.Country;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class EtlService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL = "https://restcountries.com/v3.1/all";
    private static final String RAW_DIR = "raw_data/";
    private static final String MDM_URL = "http://localhost:8080/countries";

    public String downloadAndStoreJsonChunks() throws IOException {
        Files.createDirectories(Paths.get(RAW_DIR));

        String jsonString = restTemplate.getForObject(API_URL, String.class);

        Path rawFilePath = Paths.get(RAW_DIR + "countries_raw.json");
        Files.writeString(rawFilePath, jsonString);

        List<Country> countries = objectMapper.readValue(jsonString, new TypeReference<>() {});

        int chunkSize = 50;
        int total = countries.size();
        int totalChunks = (int) Math.ceil((double) total / chunkSize);

        for (int i = 0; i < totalChunks; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, total);
            List<Country> chunk = countries.subList(start, end);

            File file = new File(RAW_DIR + "countries_chunk_" + (i + 1) + ".json");
            objectMapper.writeValue(file, chunk);
        }

        return "Total de países: " + total + ". Arquivos gerados: " + totalChunks;
    }


    public String loadAllJsonToMdm() throws IOException {
        File dir = new File(RAW_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) return "Nenhum arquivo encontrado";

        int totalEnviados = 0;

        for (File file : files) {
            List<Country> countries = objectMapper.readValue(file, new TypeReference<>() {});
            for (Country etlCountry : countries) {
                MdmCountryDTO dto = EtlToMdmTransformer.transform(etlCountry);
                restTemplate.postForEntity(MDM_URL, dto, String.class);
                totalEnviados++;
            }
        }
        return "Total de países enviados ao MDM: " + totalEnviados;
    }
}
