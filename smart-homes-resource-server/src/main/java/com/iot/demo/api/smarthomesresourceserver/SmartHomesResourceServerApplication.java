package com.iot.demo.api.smarthomesresourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SmartHomesResourceServerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SmartHomesResourceServerApplication.class, args);
    }

}
