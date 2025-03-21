package com.lackdd.deliverycalc.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.lackdd.deliverycalc.entities.Weather;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//https://javadoc.io/doc/com.fasterxml.jackson.dataformat/jackson-dataformat-xml/latest/index.html
//https://medium.com/@ya.aman.ay/how-to-serialize-and-deserialize-xml-with-jackson-in-java-991beeb2752f

@JacksonXmlRootElement(localName = "observations")
public class WeatherData {

    @JacksonXmlProperty(localName = "timestamp")
    private String timestamp;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "station")
    private List<WeatherStation> stations;

    public List<WeatherStation> getStations() {
        return stations;
    }
}

