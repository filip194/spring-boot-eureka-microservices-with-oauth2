package com.iot.demo.api.wearables.resource.server.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Wearable
{
    private final UUID wearableId;
    private final String wearableName;
    private final WearableType wearableType;
}

