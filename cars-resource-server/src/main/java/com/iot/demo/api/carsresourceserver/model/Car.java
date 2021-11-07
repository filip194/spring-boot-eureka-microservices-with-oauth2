package com.iot.demo.api.carsresourceserver.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Car
{
    private final UUID carId;
    private final String carManufacturer;
    private final String carModel;
}

