package com.instagenius.userservice.infrastructure.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS_CLAIM);
        if (realmAccess != null) {
            Object rolesObject = realmAccess.get(ROLES_CLAIM);
            if (rolesObject instanceof Collection<?> roles) {
                for (Object role : roles) {
                    if (role instanceof String) {
                        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
                    }
                }
            }
        }

        return authorities;
    }
}
