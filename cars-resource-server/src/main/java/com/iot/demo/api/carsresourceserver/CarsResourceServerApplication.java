package com.iot.demo.api.carsresourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CarsResourceServerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CarsResourceServerApplication.class, args);
    }

}
