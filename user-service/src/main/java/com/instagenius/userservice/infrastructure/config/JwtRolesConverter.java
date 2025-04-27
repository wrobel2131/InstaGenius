package com.instagenius.userservice.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class JwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String RESOURCE_ACCESS_CLAIM = "resource_access";
    private static final String REALM_ACCESS_CLAIM = "realm_access";

    private static final String ROLES_CLAIM = "roles";


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        log.info("Converting Jwt {}", jwt);
        Collection<GrantedAuthority> authorities = new HashSet<>();

        // Extract realm roles
        Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS_CLAIM);
        if (realmAccess != null && realmAccess.containsKey(ROLES_CLAIM)) {
            Object rolesObject = realmAccess.get(ROLES_CLAIM);
            if (rolesObject instanceof Collection<?> roles) {
                for (Object role : roles) {
                    if (role instanceof String) {
                        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
                    }
                }
            }
        }

        Map<String, Object> resourceAccess = jwt.getClaimAsMap(RESOURCE_ACCESS_CLAIM);
        if (resourceAccess != null) {
            resourceAccess.forEach((clientId, clientObject) -> {
                if (clientObject instanceof Map) {
                    Map<String, Object> clientResource = (Map<String, Object>) clientObject;

                    if (clientResource.containsKey(ROLES_CLAIM)) {
                        Object rolesObject = clientResource.get(ROLES_CLAIM);
                        if (rolesObject instanceof Collection<?> roles) {
                            for (Object role : roles) {
                                if (role instanceof String) {
                                    authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
                                }
                            }
                        }
                    }
                }
            });
        }

        log.info("authorities {}", authorities);

        return authorities;
    }
}
