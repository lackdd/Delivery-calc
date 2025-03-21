package com.lackdd.deliverycalc;

import com.lackdd.deliverycalc.entities.Weather;
import com.lackdd.deliverycalc.repositories.WeatherRepository;
import com.lackdd.deliverycalc.services.DeliveryFeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DeliveryFeeServiceTest {

    private final DeliveryFeeService deliveryFeeService;
    private final WeatherRepository weatherRepository;

    @Autowired
    public DeliveryFeeServiceTest(DeliveryFeeService deliveryFeeService, WeatherRepository weatherRepository) {this.deliveryFeeService = deliveryFeeService; this.weatherRepository = weatherRepository;}

    @Test
    void calculateRBFforTartuCar() {
        String fee = deliveryFeeService.getDeliveryFee("Tartu", "Car");
        assertTrue(fee.equals("3.5€"));
    }

    @Test
    void calculateRBFforTallinnCar() {
        String fee = deliveryFeeService.getDeliveryFee("Tallinn", "Car");
        assertTrue(fee.equals("4€"));
    }

    @Test
    void calculateRBFforPärnuCar() {
        String fee = deliveryFeeService.getDeliveryFee("Pärnu", "Car");
        assertTrue(fee.equals("3€"));
    }

    @Test
    void fetchWeatherData() {
        List<Weather> weatherList = weatherRepository.findAll();
        assertFalse(weatherList.isEmpty(), "Empty Weather Data");
    }



}
