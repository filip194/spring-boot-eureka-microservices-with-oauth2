package com.iot.demo.api.mvcwebclient.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Vehicle
{
    private final UUID vehicleId;
    private final String vehicleManufacturer;
    private final String vehicleModel;
}

