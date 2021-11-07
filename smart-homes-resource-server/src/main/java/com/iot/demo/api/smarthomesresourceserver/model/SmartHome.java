package com.iot.demo.api.smarthomesresourceserver.model;

import java.util.Collection;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmartHome
{
    private final UUID smartHomeId;
    private final String smartHomeName;
    private final Collection<Device> smartHomeDevices;
}

