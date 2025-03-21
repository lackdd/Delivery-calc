package com.lackdd.deliverycalc.services;

import com.lackdd.deliverycalc.entities.Weather;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class DeliveryFeeService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeService.class);

    public String getDeliveryFee(String cityName, String vehicle) {
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

        WebClient client = WebClient.create("https://api.cron-job.org/");

        /*Mono<ResponseEntity<Weather>> result = client.get()
                .uri("/jobs").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Weather.class);*/

        // wanted to push to git, temporary return value
        return "temp";
    }
}
