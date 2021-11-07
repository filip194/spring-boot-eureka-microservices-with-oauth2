package com.iot.demo.api.wearables.resource.server.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.demo.api.wearables.resource.server.model.Wearable;
import com.iot.demo.api.wearables.resource.server.model.WearableType;

@RestController
@RequestMapping(path = "/wearables")
public class WearablesController
{
    private final Environment env;

    public WearablesController(Environment environment)
    {
        this.env = environment;
    }

    @GetMapping(path = "/port")
    public String getPort()
    {
        return "Wearables Resource Server working on port: " + env.getProperty("local.server.port");
    }

    @GetMapping("/all")
    public Collection<Wearable> getAllWearables()
    {
        return Arrays.asList(new Wearable(UUID.randomUUID(), "Really Really Smart Watch", WearableType.SMARTWATCH),
                new Wearable(UUID.randomUUID(), "Very Smart Bracelet", WearableType.SMARTJEWELRY));
    }
}

