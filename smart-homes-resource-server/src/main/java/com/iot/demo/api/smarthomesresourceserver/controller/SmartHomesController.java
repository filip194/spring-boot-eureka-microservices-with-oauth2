package com.iot.demo.api.smarthomesresourceserver.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.demo.api.smarthomesresourceserver.model.Device;
import com.iot.demo.api.smarthomesresourceserver.model.Sensor;
import com.iot.demo.api.smarthomesresourceserver.model.SmartHome;

@RestController
@RequestMapping("/smart-homes")
public class SmartHomesController
{
    private final Environment env;

    public SmartHomesController(Environment environment)
    {
        this.env = environment;
    }

    @GetMapping(path = "/port")
    public String getPort()
    {
        return "Smart Homes Resource Server working on port: " + env.getProperty("local.server.port");
    }

    @GetMapping(path = "/all")
    public Collection<SmartHome> getSmartHomes()
    {
        //1
        final Collection<Sensor> tempSensors = List.of(new Sensor(UUID.randomUUID(), "tempSensor1", 25),
                new Sensor(UUID.randomUUID(), "tempSensor2", 30));
        final Collection<Sensor> humiditySensors = List.of(new Sensor(UUID.randomUUID(), "humiditySensor1", 75.2),
                new Sensor(UUID.randomUUID(), "humiditySensor2", 80.7));
        final Collection<Device> devices = List.of(new Device(UUID.randomUUID(), "thermometer1", tempSensors),
                new Device(UUID.randomUUID(), "hygrometer1", humiditySensors));
        final SmartHome simpleSmartHome = new SmartHome(UUID.randomUUID(), "Simple Smart Home System", devices);

        //2
        final Collection<Sensor> securityCameras = List.of(new Sensor(UUID.randomUUID(), "infraredSensor1", 72),
                new Sensor(UUID.randomUUID(), "infraredSensor2", 65));
        final Collection<Sensor> videoDoorBells = List.of(new Sensor(UUID.randomUUID(), "buttonPressSensor1", 75.2),
                new Sensor(UUID.randomUUID(), "buttonPressSensor2", 80.7));

        final Collection<Device> securityDevices = List.of(
                new Device(UUID.randomUUID(), "securityCamera1", securityCameras),
                new Device(UUID.randomUUID(), "videoDoorBell", videoDoorBells));

        final SmartHome securitySmartHome = new SmartHome(UUID.randomUUID(), "Security Based Smart Home System",
                securityDevices);

        return List.of(simpleSmartHome, securitySmartHome);
    }
}

