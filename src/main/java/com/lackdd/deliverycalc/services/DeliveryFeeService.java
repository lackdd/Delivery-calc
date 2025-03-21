package com.lackdd.deliverycalc.services;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

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
                }
                break;
            case "tartu":
                if(vehicle.toLowerCase() == "car"){
                    RBF = BigDecimal.valueOf(3.5);
                } else if(vehicle.toLowerCase() == "scooter"){
                    RBF = BigDecimal.valueOf(3);
                } else if(vehicle.toLowerCase() == "bike"){
                    RBF = BigDecimal.valueOf(2.5);
                }
                break;
            case "pärnu":
                if(vehicle.toLowerCase() == "car"){
                    RBF = BigDecimal.valueOf(3);
                } else if(vehicle.toLowerCase() == "scooter"){
                    RBF = BigDecimal.valueOf(2.5);
                } else if(vehicle.toLowerCase() == "bike"){
                    RBF = BigDecimal.valueOf(2);
                }
                break;
            default:
                logger.error("Failed to calculate regional base fee.");
        }
        // wanted to push to git, temporary return value
        return "temp";
    }
}
