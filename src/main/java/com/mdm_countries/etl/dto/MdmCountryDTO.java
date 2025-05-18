package com.mdm_countries.etl.dto;

import lombok.Data;

@Data
public class MdmCountryDTO {
    private String cca2;
    private String ccn3;
    private String cioc;
    private Boolean independent;
    private String status;
    private Boolean unMember;
    private String region;
    private String subregion;
    private Double area;
    private Long population;
    private String fifa;
    private String startOfWeek;

    private String nameCommon;
    private String nameOfficial;
    private String capital;
    private String tld;
    private String altSpellings;
    private String languages;
    private String currencies;
    private String borders;

    private String flagPng;
    private String flagSvg;
    private String flagAlt;
    private String googleMaps;
    private String openStreetMaps;

    private String coatOfArmsPng;
    private String coatOfArmsSvg;

    private String carSide;
    private String carSigns;

    private String timezones;
    private String continents;

    private String iddRoot;
    private String iddSuffixes;

    private String latlng;
    private Double capitalInfoLat;
    private Double capitalInfoLng;

    private String postalCodeFormat;
    private String postalCodeRegex;

    private String demonymF;
    private String demonymM;
}
