package com.management.progettodigestioneacquisti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProgettoDiGestioneAcquistiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgettoDiGestioneAcquistiApplication.class, args);
    }

}
