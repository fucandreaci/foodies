package com.azienda.foodies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.azienda.foodies.service.ServiceManager;

@SpringBootApplication
public class FoodiesApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(FoodiesApplication.class, args);
        configurableApplicationContext.getBean("AdminBean");
    }

}
