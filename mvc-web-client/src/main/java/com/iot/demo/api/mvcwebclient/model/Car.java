package com.iot.demo.api.mvcwebclient.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car
{
    private UUID carId;
    private String carManufacturer;
    private String carModel;
}

