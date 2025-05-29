package com.mdm_countries.etl.dto;

import lombok.Data;
import java.util.List;

@Data
public class MdmCountryDTO {
    private long countryId;
    private String countryName;
    private Integer numericCode;
    private String capitalCity;
    private Long population;
    private Double area;
    private List<MdmCurrencyDTO> currencies;
}

