package com.lackdd.deliverycalc.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lackdd.deliverycalc.dto.WeatherData;
import com.lackdd.deliverycalc.dto.WeatherStation;
import com.lackdd.deliverycalc.entities.Weather;
import com.lackdd.deliverycalc.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final WebClient client = WebClient.create();
    private final XmlMapper xmlMapper = new XmlMapper();

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {this.weatherRepository = weatherRepository;}

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
                    .filter(station -> "Tallinn-Harku".equals(station.getName()) || "Tartu-Tõravere".equals(station.getName()) || "Pärnu".equals(station.getName()))
                    .collect(Collectors.toList());
            System.out.println("Filtered Stations: " + filteredStations.stream().map(WeatherStation::getName).collect(Collectors.toList()));

            // map list of weatherstations to weather entity
            List<Weather> weathers = filteredStations.stream().map(station -> new Weather(
            null, station.getName(), station.getWmocode(), BigDecimal.valueOf(station.getAirtemperature()), BigDecimal.valueOf(station.getWindspeed()), station.getPhenomenon(), LocalDateTime.now()
            )).collect(Collectors.toList());
            weatherRepository.saveAll(weathers);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
