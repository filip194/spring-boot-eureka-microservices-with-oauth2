package com.iot.demo.api.vehiclesresourceserver.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle
{
    private UUID vehicleId;
    private String vehicleManufacturer;
    private String vehicleModel;
}

