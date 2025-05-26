package com.mdm_countries.etl.mapper;

import com.mdm_countries.etl.model.Country;
import com.mdm_countries.etl.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EtlToMdmTransformer {

    public static MdmCountryDTO transform(Country etlCountry) {
        MdmCountryDTO dto = new MdmCountryDTO();

        dto.setCountryName(etlCountry.getName().getCommon());
        dto.setNumericCode(parseIntOrNull(etlCountry.getCcn3()));
        dto.setCapitalCity(
                etlCountry.getCapital() != null && !etlCountry.getCapital().isEmpty()
                        ? etlCountry.getCapital().get(0)
                        : null
        );
        dto.setPopulation(etlCountry.getPopulation());
        dto.setArea(etlCountry.getArea());

        dto.setCurrencies(parseCurrencies(etlCountry.getCurrencies()));

        return dto;
    }

    private static Integer parseIntOrNull(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static List<MdmCurrencyDTO> parseCurrencies(Map<String, Object> currenciesMap) {
        List<MdmCurrencyDTO> list = new ArrayList<>();
        if (currenciesMap == null) return list;

        for (var entry : currenciesMap.entrySet()) {
            String code = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                Map<?, ?> currencyData = (Map<?, ?>) value;
                MdmCurrencyDTO dto = new MdmCurrencyDTO();
                dto.setCurrencyCode(code);
                dto.setCurrencyName((String) currencyData.get("name"));
                dto.setCurrencySymbol((String) currencyData.get("symbol"));
                list.add(dto);
            }
        }

        return list;
    }
}
