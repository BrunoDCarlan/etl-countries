package com.mdm_countries.etl.controller;

import com.mdm_countries.etl.service.EtlService;

import com.mdm_countries.etl.model.Download;

import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/etl")
public class EtlController {

    private final EtlService etlService;

    public EtlController(EtlService etlService) {
        this.etlService = etlService;
    }

    @GetMapping("/download/{providerId}")
    public String downloadFromProvider(@PathVariable Long providerId) throws IOException, InterruptedException {
        return etlService.downloadAndStoreJsonChunks(providerId);
    }

    @GetMapping("/load")
    public String loadETLToMdm() throws Exception {
        return etlService.loadAllJsonToMdm();
    }

    @GetMapping("/downloads")
    public List<Download> getAllDownloads() throws IOException, InterruptedException {
        return etlService.getAllDownloads();
    }
}