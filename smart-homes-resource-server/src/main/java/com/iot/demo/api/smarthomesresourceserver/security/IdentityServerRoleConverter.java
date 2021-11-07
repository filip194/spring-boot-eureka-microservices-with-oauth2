package com.iot.demo.api.smarthomesresourceserver.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class IdentityServerRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{

    @Override
    public Collection<GrantedAuthority> convert(final Jwt jwt)
    {

        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty())
        {
            return Collections.emptyList();
        }

        Collection<GrantedAuthority> authorities = ((List<String>) realmAccess.get("roles")).stream()
                .map(roleName -> "ROLE_" + roleName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return authorities;
    }

}
