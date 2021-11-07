package com.iot.demo.api.smarthomesresourceserver.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sensor
{
    private final UUID sensorId;
    private final String sensorName;
    private final Number sensorValue;
}

