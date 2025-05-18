package com.mdm_countries.etl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private Name name;

    @JsonProperty("cca2")
    private String cca2;

    @JsonProperty("ccn3")
    private String ccn3;

    @JsonProperty("cioc")
    private String cioc;

    private Boolean independent;
    private String status;

    @JsonProperty("unMember")
    private Boolean unMember;

    private String region;
    private String subregion;
    private Double area;
    private Long population;
    private String fifa;

    @JsonProperty("startOfWeek")
    private String startOfWeek;

    private List<String> capital;
    private List<String> tld;
    private List<String> altSpellings;
    private Map<String, String> languages;
    private Map<String, Object> currencies;
    private List<String> borders;

    private Map<String, String> flags;
    private Map<String, String> coatOfArms;
    private Map<String, Object> maps;
    private Map<String, Object> car;
    private List<String> timezones;
    private List<String> continents;
    private Map<String, Object> idd;
    private List<Double> latlng;
    private CapitalInfo capitalInfo;
    private Map<String, String> postalCode;
    private Map<String, Map<String, String>> demonyms;
}
