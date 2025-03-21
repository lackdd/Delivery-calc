package com.lackdd.deliverycalc.controllers;

import com.lackdd.deliverycalc.services.DeliveryFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class DeliveryFeeController {
    private final DeliveryFeeService deliveryFeeService;

    @Autowired
    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {this.deliveryFeeService = deliveryFeeService;}

    @GetMapping("/delivery-fee")
    public ResponseEntity<String> getDeliveryFee(@RequestParam(name = "cityName") String cityName, @RequestParam(name = "vehicle") String vehicle) {
        try {
            String deliveryFee = deliveryFeeService.getDeliveryFee(cityName, vehicle);
            return ResponseEntity.ok(deliveryFee);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request failed: " + e);
        }
    }
}
