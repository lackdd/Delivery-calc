package com.lackdd.deliverycalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DeliveryCalcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryCalcApplication.class, args);
    }

}
