package com.iot.demo.api.carsresourceserver.controller;

import java.util.Collection;
import java.util.List;
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

import com.iot.demo.api.carsresourceserver.model.Car;

@RestController
@RequestMapping(path = "/cars")
public class CarsController
{
    private final static List<Car> CARS = List.of(
            new Car(UUID.fromString("6799627f-a4da-4902-92f1-dc83e96d9a67"), "Trabant", "601"),
            new Car(UUID.randomUUID(), "Zastava", "750"), new Car(UUID.randomUUID(), "Yugo", "45"));

    private final Environment env;

    public CarsController(Environment environment)
    {
        this.env = environment;
    }

    @GetMapping(path = "/port")
    public String getPort()
    {
        return "Cars Resource Server working on port: " + env.getProperty("local.server.port");
    }

    @GetMapping("/all")
    public Collection<Car> getAllCars()
    {
        return CARS;
    }

    // wouldn't work without .toString
    // @PostAuthorize(value = "returnObject.carId == #jwt.subject")
    @PostAuthorize(value = "returnObject.carId.toString == #jwt.subject")
    @GetMapping("/{id}")
    public Car getCar(@PathVariable String id, @AuthenticationPrincipal Jwt jwt)
    {
        return CARS.get(0);
    }

    // @Secured(value=...) is authority so ROLE_ prefix must be added
    // @Secured(value = "ROLE_iot_developer")
    // pre and post authorize methods support security expressions
    // @PreAuthorize(value = "hasRole('iot_developer')")
    // @PreAuthorize(value = "hasAuthority('ROLE_iot_developer')")
    @PreAuthorize(value = "#id == #jwt.subject") // this subject is 'sub' in JSON
    @DeleteMapping(path = "/{id}")
    public String deleteCar(@PathVariable String id, @AuthenticationPrincipal Jwt jwt)
    {
        return "Deleted car with id: " + id + ", and JWT subject: " + jwt.getSubject();
    }

}

