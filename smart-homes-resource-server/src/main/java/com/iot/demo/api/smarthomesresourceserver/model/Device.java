package com.iot.demo.api.smarthomesresourceserver.model;

import java.util.Collection;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Device
{
    private final UUID deviceId;
    private final String deviceName;
    private final Collection<Sensor> deviceSensors;
}

