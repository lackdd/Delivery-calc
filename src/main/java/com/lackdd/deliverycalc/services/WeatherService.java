package com.lackdd.deliverycalc.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lackdd.deliverycalc.dto.WeatherData;
import com.lackdd.deliverycalc.dto.WeatherStation;
import com.lackdd.deliverycalc.entities.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final WebClient client = WebClient.create();
    private final XmlMapper xmlMapper = new XmlMapper();

    public void fetchWeatherData() {
        try {
            // Weather data from Keskkonnaagentuur @ ilmateenistus.ee
            String response = client.get()
                    .uri("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            WeatherData weatherData = xmlMapper.readValue(response, WeatherData.class);
            List<WeatherStation> filteredStations = weatherData.getStations().stream()
                    .filter(station -> "Tallinn-Harku".equals(station.getName()))
                    .collect(Collectors.toList());
            System.out.println("Filtered Stations: " + filteredStations);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
