// EtlToMdmTransformer.java - PROJETO ETL
package com.mdm_countries.etl.mapper;

import com.mdm_countries.etl.dto.MdmCountryDTO;
import com.mdm_countries.etl.model.Country;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class EtlToMdmTransformer {

    public static MdmCountryDTO transform(Country etl) {
        MdmCountryDTO dto = new MdmCountryDTO();

        dto.setCca2(etl.getCca2());
        dto.setCcn3(etl.getCcn3());
        dto.setCioc(etl.getCioc());
        dto.setIndependent(etl.getIndependent());
        dto.setStatus(etl.getStatus());
        dto.setUnMember(etl.getUnMember());
        dto.setRegion(etl.getRegion());
        dto.setSubregion(etl.getSubregion());
        dto.setArea(etl.getArea());
        dto.setPopulation(etl.getPopulation());
        dto.setFifa(etl.getFifa());
        dto.setStartOfWeek(etl.getStartOfWeek());

        if (etl.getName() != null) {
            dto.setNameCommon(etl.getName().getCommon());
            dto.setNameOfficial(etl.getName().getOfficial());
        }

        dto.setCapital(joinList(etl.getCapital()));
        dto.setTld(joinList(etl.getTld()));
        dto.setAltSpellings(joinList(etl.getAltSpellings()));
        dto.setLanguages(joinMapValues(etl.getLanguages()));
        dto.setCurrencies(etl.getCurrencies() != null ? etl.getCurrencies().toString() : null);
        dto.setBorders(joinList(etl.getBorders()));

        if (etl.getFlags() != null) {
            dto.setFlagPng(etl.getFlags().get("png"));
            dto.setFlagSvg(etl.getFlags().get("svg"));
            dto.setFlagAlt(etl.getFlags().get("alt"));
        }

        if (etl.getMaps() != null) {
            dto.setGoogleMaps((String) etl.getMaps().get("googleMaps"));
            dto.setOpenStreetMaps((String) etl.getMaps().get("openStreetMaps"));
        }

        if (etl.getCoatOfArms() != null) {
            dto.setCoatOfArmsPng(etl.getCoatOfArms().get("png"));
            dto.setCoatOfArmsSvg(etl.getCoatOfArms().get("svg"));
        }

        if (etl.getCar() != null) {
            dto.setCarSide((String) etl.getCar().get("side"));
            dto.setCarSigns(joinList((List<String>) etl.getCar().get("signs")));
        }

        dto.setTimezones(joinList(etl.getTimezones()));
        dto.setContinents(joinList(etl.getContinents()));

        if (etl.getIdd() != null) {
            dto.setIddRoot((String) etl.getIdd().get("root"));
            dto.setIddSuffixes(joinList((List<String>) etl.getIdd().get("suffixes")));
        }

        dto.setLatlng(joinDoubleList(etl.getLatlng()));

        if (etl.getCapitalInfo() != null && etl.getCapitalInfo().getLatlng() != null && etl.getCapitalInfo().getLatlng().size() >= 2) {
            dto.setCapitalInfoLat(etl.getCapitalInfo().getLatlng().get(0));
            dto.setCapitalInfoLng(etl.getCapitalInfo().getLatlng().get(1));
        }

        if (etl.getPostalCode() != null) {
            dto.setPostalCodeFormat((String) etl.getPostalCode().get("format"));
            dto.setPostalCodeRegex((String) etl.getPostalCode().get("regex"));
        }

        if (etl.getDemonyms() != null) {
            Map<String, String> eng = (Map<String, String>) etl.getDemonyms().get("eng");
            if (eng != null) {
                dto.setDemonymF(eng.get("f"));
                dto.setDemonymM(eng.get("m"));
            }
        }

        return dto;
    }

    private static String joinList(List<String> list) {
        return list != null ? String.join(", ", list) : null;
    }

    private static String joinMapValues(Map<String, String> map) {
        return map != null ? String.join(", ", map.values()) : null;
    }

    private static String joinDoubleList(List<Double> list) {
        if (list == null) return null;
        StringJoiner sj = new StringJoiner(", ");
        for (Double d : list) sj.add(String.valueOf(d));
        return sj.toString();
    }
}
