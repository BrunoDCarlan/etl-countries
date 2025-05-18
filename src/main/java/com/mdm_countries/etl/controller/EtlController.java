package com.mdm_countries.etl.controller;

import com.mdm_countries.etl.service.EtlService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/etl")
public class EtlController {

    private final EtlService etlService;

    public EtlController(EtlService etlService) {
        this.etlService = etlService;
    }

    @GetMapping("/start")
    public String startETL() throws IOException, InterruptedException {
        return etlService.downloadAndStoreJsonChunks();
    }

    @GetMapping("/load")
    public String loadETLToMdm() throws Exception {
        return etlService.loadAllJsonToMdm();
    }
}