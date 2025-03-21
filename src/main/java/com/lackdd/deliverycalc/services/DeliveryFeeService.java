package com.lackdd.deliverycalc.services;

import com.lackdd.deliverycalc.entities.Weather;
import com.lackdd.deliverycalc.repositories.WeatherRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryFeeService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeService.class);

    private final WeatherRepository weatherRepository;

    @Autowired
    DeliveryFeeService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    private String getStationName(String cityName) {
        if (cityName.equalsIgnoreCase("tallinn")) {
            return "tallinn-harku";
        } else if (cityName.equalsIgnoreCase("tartu")) {
            return "tartu-tõravere";
        } else if (cityName.equalsIgnoreCase("pärnu")) {
            return "pärnu";
        }
        throw new IllegalArgumentException("Invalid city name: " + cityName);
    }

    public String getDeliveryFee(String cityName, String vehicle) {
        String stationName = getStationName(cityName);

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<Weather> currentWeatherList = weatherRepository.findByLatestDate(oneHourAgo);
        Weather selectedWeather = currentWeatherList.stream().filter(weather -> weather.getStationName().equalsIgnoreCase(stationName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No weather data found for city: " + cityName));

        BigDecimal airTemperature = selectedWeather.getAirTemperature();
        BigDecimal windSpeed = selectedWeather.getWindSpeed();
        String weatherPhenomenon = selectedWeather.getWeatherPhenomenon();

        BigDecimal ATEF = BigDecimal.valueOf(0);
        BigDecimal WSEF = BigDecimal.valueOf(0);
        BigDecimal WPEF = BigDecimal.valueOf(0);

        if(vehicle.equalsIgnoreCase("scooter") || vehicle.equalsIgnoreCase("bike")) {
            if(airTemperature.compareTo(BigDecimal.valueOf(-10)) < 0) {
                ATEF = BigDecimal.valueOf(1);
            } else if (airTemperature.compareTo(BigDecimal.valueOf(-10)) > 0 && airTemperature.compareTo(BigDecimal.ZERO) < 0)
            {
                ATEF = BigDecimal.valueOf(0.5);
            }

            if(weatherPhenomenon.equalsIgnoreCase("snow") || weatherPhenomenon.equalsIgnoreCase("sleet")) {
                WPEF = BigDecimal.valueOf(1);
            } else if (weatherPhenomenon.equalsIgnoreCase("rain")) {
                WPEF = BigDecimal.valueOf(0.5);
            } else if (weatherPhenomenon.equalsIgnoreCase("glaze") || weatherPhenomenon.equalsIgnoreCase("hail") || weatherPhenomenon.equalsIgnoreCase("thunder")) {
                throw new IllegalArgumentException("Error: Usage of selected vehicle type is forbidden.");
            }
        }

        if(vehicle.equalsIgnoreCase("bike")) {
            if (windSpeed.compareTo(BigDecimal.valueOf(10)) > 0 && windSpeed.compareTo(BigDecimal.valueOf(20)) < 0)
            {
                WSEF = BigDecimal.valueOf(0.5);
            } else if(windSpeed.compareTo(BigDecimal.valueOf(20)) > 0) {
                throw new IllegalArgumentException("Error: Usage of selected vehicle type is forbidden.");
            }
        }


        BigDecimal RBF = BigDecimal.valueOf(0);
        switch (cityName.toLowerCase()) {
            case "tallinn":
                if (vehicle.trim().equalsIgnoreCase("car")) {
                    RBF = BigDecimal.valueOf(4);
                } else if (vehicle.trim().equalsIgnoreCase("scooter")) {
                    RBF = BigDecimal.valueOf(3.5);
                } else if (vehicle.trim().equalsIgnoreCase("bike")) {
                    RBF = BigDecimal.valueOf(3);
                } else {
                    logger.error("Failed to calculate regional base fee. Invalid vehicle name.");
                    logger.error("Invalid vehicle name: {}", vehicle);
                }
                break;
            case "tartu":
                if (vehicle.trim().equalsIgnoreCase("car")) {
                    RBF = BigDecimal.valueOf(3.5);
                } else if (vehicle.trim().equalsIgnoreCase("scooter")) {
                    RBF = BigDecimal.valueOf(3);
                } else if (vehicle.trim().equalsIgnoreCase("bike")) {
                    RBF = BigDecimal.valueOf(2.5);
                } else {
                    logger.error("Failed to calculate regional base fee. Invalid vehicle name.");
                }
                break;
            case "pärnu":
                if (vehicle.trim().equalsIgnoreCase("car")) {
                    RBF = BigDecimal.valueOf(3);
                } else if (vehicle.trim().equalsIgnoreCase("scooter")) {
                    RBF = BigDecimal.valueOf(2.5);
                } else if (vehicle.trim().equalsIgnoreCase("bike")) {
                    RBF = BigDecimal.valueOf(2);
                } else {
                    logger.error("Failed to calculate regional base fee. Invalid vehicle name.");
                }
                break;
            default:
                logger.error("Failed to calculate regional base fee. Invalid City name.");
        }

        BigDecimal totalFee = RBF.add(ATEF).add(WSEF).add(WPEF);
        return totalFee + "€";
    }
}
