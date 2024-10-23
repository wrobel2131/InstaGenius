package com.instagenius.postmanagementservice.infrastructure.rest;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getToken() != null) {
            String token = authentication.getToken().getTokenValue();

            requestTemplate.header("Authorization", "Bearer " + token);
        }

    }
}
