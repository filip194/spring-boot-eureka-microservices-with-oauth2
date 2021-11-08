package com.iot.demo.api.mvcwebclient.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientsConfig
{

    // deprecated for use with OAuth2 client libraries, WebClient should be used instead
    // used with DeprecatedCarsController
    @Deprecated
    @Bean
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }

    // Filter - to inject Access Token to requests so this WebClient can work with OAuth2,
    // we will need to use filter on WebClient that will include Access Token into every HTTP request this client app will send
    @Bean // if multiple beans exist of the same type, use @Resource to inject them by name, instead @Autowired by type
    public WebClient getWebClient(ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository)
    {
        // this filter depends on 2 objects (can be injected as Bean method parameter):
        // - ClientRegistrationRepository is repository of registered OAuth2 clients
        // - OAuth2AuthorizedClientRepository stores information on clients that have already been authorized
        final ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Filter = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrationRepository, oAuth2AuthorizedClientRepository);

        // setting filter with default OAuth2 authorized client
        // automatically detects client configuration to use based on the current Authentication object
        oauth2Filter.setDefaultOAuth2AuthorizedClient(true);

        // also need to apply OAuth2 configuration to WebClient object
        return WebClient.builder().apply(oauth2Filter.oauth2Configuration()).build();
    }
}

