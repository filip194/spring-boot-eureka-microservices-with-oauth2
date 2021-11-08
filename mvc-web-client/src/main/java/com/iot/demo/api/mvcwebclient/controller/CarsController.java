package com.iot.demo.api.mvcwebclient.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.iot.demo.api.mvcwebclient.model.Car;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// regular Spring MVC controller
@Controller
public class CarsController
{
    private final static String URL = "http://localhost:9092/cars/all";
    private final WebClient webClient;

    public CarsController(WebClient webClient)
    {
        this.webClient = webClient;
    }

    // this path will trigger when user requests cars view in their browser
    @GetMapping(path = "/cars/all")
    public String getCars(Model model)
    {
        // all that code that reads and get access token is not needed anymore, WebClient handles it internally
        // with applied filter

        final List<Car> cars = webClient.get()
                .uri(URL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Car>>() {})
                .block();

        // addAttribute [key - value] pair; by key I can later access list object of cars in HTML page/Thymeleaf
        model.addAttribute("cars", cars);

        // name of the view; name of the web page that will display list of cars
        return "cars";
    }
}

