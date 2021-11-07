package com.iot.demo.api.smarthomesresourceserver.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        final JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // set up desired converter
        converter.setJwtGrantedAuthoritiesConverter(new IdentityServerRoleConverter());

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/smart-homes/**")
                .hasAuthority("ROLE_iot_developer")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(converter); // applying JWT Authentication converter

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

