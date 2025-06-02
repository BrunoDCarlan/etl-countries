package com.mdm_countries.etl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProviderDTO {
    @NotBlank
    private String providerName;
    @NotBlank
    private String providerUrl;
}
