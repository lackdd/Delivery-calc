package com.lackdd.deliverycalc.schedulingtasks;

import com.lackdd.deliverycalc.entities.Weather;
import com.lackdd.deliverycalc.services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class ScheduledTasks {
    private final WeatherService weatherService;

    @Autowired
    public ScheduledTasks(WeatherService weatherService) {this.weatherService = weatherService;}

    // using https://spring.io/guides/gs/scheduling-tasks as basis for learning

    @Scheduled(cron = "0 15 * * * *")
    //@Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        weatherService.fetchWeatherData();
    }
}
