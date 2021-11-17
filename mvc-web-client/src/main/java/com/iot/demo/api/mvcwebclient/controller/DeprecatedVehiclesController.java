package com.iot.demo.api.mvcwebclient.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.iot.demo.api.mvcwebclient.model.Vehicle;

import lombok.extern.slf4j.Slf4j;

/**
 * Deprecated version of controller using RestTemplate
 */

@Deprecated
@Slf4j
// regular Spring MVC controller
@Controller
public class DeprecatedVehiclesController
{
    private final static String URL = "http://localhost:9092/vehicles/all";

    // this service needs to be used together withOAuth2AuthenticationToken
    // service is available automatically through Spring Security context
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private final RestTemplate restTemplate;

    public DeprecatedVehiclesController(OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
            RestTemplate restTemplate)
    {
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
        this.restTemplate = restTemplate;

    }

    // this path will trigger when user requests vehicles view in their browser
    @GetMapping(path = "/deprecated/vehicles/all")
    public String getVehicles(Model model, @AuthenticationPrincipal OidcUser principal/*, Authentication authentication*/)
    {
        // this is another way to get Authentication object from Spring Security context
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // it will actually be OAuth2 Authentication token, so we need to cast it to OAuth2AuthenticationToken
        final OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;

        // this oauth2 authentication token will need to be used together with OAuth2AuthorizedClientService to get the access token value
        // getting OAuth2AuthorizedClient to get access token
        final OAuth2AuthorizedClient oAuth2Client = oAuth2AuthorizedClientService.loadAuthorizedClient(
                oAuth2Token.getAuthorizedClientRegistrationId(), oAuth2Token.getName());

        // getting access token
        final String jwtAccessToken = oAuth2Client.getAccessToken().getTokenValue();
        log.info("JWT Access Token: {}", jwtAccessToken);

        log.info("PRINCIPAL: {}", principal);

        final OidcIdToken idToken = principal.getIdToken();
        final String tokenValue = idToken.getTokenValue();

        log.info("ID Token value: {}", tokenValue);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAccessToken);
        final HttpEntity<List<Vehicle>> entity = new HttpEntity<>(headers);

        final ResponseEntity<List<Vehicle>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity,
                new ParameterizedTypeReference<>()
                {
                });

        final List<Vehicle> vehicles = responseEntity.getBody();

        // addAttribute [key - value] pair; by key I can later access list object of vehicles in HTML page/Thymeleaf
        model.addAttribute("vehicles", vehicles);

        // name of the view; name of the web page that will display list of vehicles
        return "vehicles";
    }
}

