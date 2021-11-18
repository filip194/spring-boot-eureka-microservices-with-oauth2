package com.iot.demo.api.vehiclesresourceserver.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.demo.api.vehiclesresourceserver.model.Vehicle;

@RestController
@RequestMapping(path = "/vehicles")
public class VehiclesController
{
    private final static List<Vehicle> VEHICLES = List.of(
            new Vehicle(UUID.fromString("6799627f-a4da-4902-92f1-dc83e96d9a67"), "Cargo Ship", "X502"),
            new Vehicle(UUID.randomUUID(), "Cargo Jet", "Z150"), new Vehicle(UUID.randomUUID(), "Tow Truck", "Y45"));

    private final Environment env;

    public VehiclesController(Environment environment)
    {
        this.env = environment;
    }

    @GetMapping(path = "/port")
    public String getPort()
    {
        return "Vehicles Resource Server working on port: " + env.getProperty("local.server.port");
    }

    @GetMapping(path = "/token")
    public Map<String, Jwt> getToken(@AuthenticationPrincipal Jwt jwt)
    {
        return Collections.singletonMap("principal", jwt);
    }

    @GetMapping("/all")
    public Collection<Vehicle> getAllVehicles()
    {
        return VEHICLES;
    }

    // wouldn't work without .toString
    // @PostAuthorize(value = "returnObject.vehicleId == #jwt.subject")
    @PostAuthorize(value = "returnObject.vehicleId.toString == #jwt.subject")
    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable String id, @AuthenticationPrincipal Jwt jwt)
    {
        return VEHICLES.get(0);
    }

    // @Secured(value=...) is authority so ROLE_ prefix must be added
    // @Secured(value = "ROLE_iot_developer")
    // pre and post authorize methods support security expressions
    // @PreAuthorize(value = "hasRole('iot_developer')")
    // @PreAuthorize(value = "hasAuthority('ROLE_iot_developer')")
    @PreAuthorize(value = "#id == #jwt.subject") // this subject is 'sub' in JSON
    @DeleteMapping(path = "/{id}")
    public String deleteVehicle(@PathVariable String id, @AuthenticationPrincipal Jwt jwt)
    {
        return "Deleted vehicle with id: " + id + ", and JWT subject: " + jwt.getSubject();
    }

}

