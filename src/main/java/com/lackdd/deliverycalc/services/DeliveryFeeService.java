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
    DeliveryFeeService(WeatherRepository weatherRepository) {this.weatherRepository = weatherRepository;}

    public String getDeliveryFee(String cityName, String vehicle) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<Weather> currentWeather = weatherRepository.findByLatestDate(oneHourAgo);

        BigDecimal RBF;
        switch (cityName.toLowerCase()) {
            case "tallinn":
                if(vehicle.toLowerCase() == "car"){
                    RBF = BigDecimal.valueOf(4);
                } else if(vehicle.toLowerCase() == "scooter"){
                    RBF = BigDecimal.valueOf(3.5);
                } else if(vehicle.toLowerCase() == "bike"){
                    RBF = BigDecimal.valueOf(3);
                } else {
                    logger.error("Failed to calculate regional base fee. Invalid vehicle name.");
                }
                break;
            case "tartu":
                if(vehicle.toLowerCase() == "car"){
                    RBF = BigDecimal.valueOf(3.5);
                } else if(vehicle.toLowerCase() == "scooter"){
                    RBF = BigDecimal.valueOf(3);
                } else if(vehicle.toLowerCase() == "bike"){
                    RBF = BigDecimal.valueOf(2.5);
                } else {
                    logger.error("Failed to calculate regional base fee. Invalid vehicle name.");
                }
                break;
            case "pärnu":
                if(vehicle.toLowerCase() == "car"){
                    RBF = BigDecimal.valueOf(3);
                } else if(vehicle.toLowerCase() == "scooter"){
                    RBF = BigDecimal.valueOf(2.5);
                } else if(vehicle.toLowerCase() == "bike"){
                    RBF = BigDecimal.valueOf(2);
                } else {
                    logger.error("Failed to calculate regional base fee. Invalid vehicle name.");
                }
                break;
            default:
                logger.error("Failed to calculate regional base fee. Invalid City name.");
        }


        // wanted to push to git, temporary return value
        return "temp";
    }
}
