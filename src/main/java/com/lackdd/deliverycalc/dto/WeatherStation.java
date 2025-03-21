package com.lackdd.deliverycalc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherStation {

    @JacksonXmlProperty(localName = "name")
    private String name;
    @JacksonXmlProperty(localName = "wmocode")
    private String wmocode;
    @JacksonXmlProperty(localName = "airtemperature")
    private Double airtemperature;
    @JacksonXmlProperty(localName = "windspeed")
    private Double windspeed;
    @JacksonXmlProperty(localName = "phenomenon")
    private String phenomenon;
}
