package com.iot.demo.api.wearables.resource.server.model;

import lombok.Getter;

@Getter
public enum WearableType
{
    SMARTWATCH("Smartwatch"), SMARTJEWELRY("Smart jewelry");

    private final String name;

    WearableType(String name)
    {
        this.name = name;
    }
}
