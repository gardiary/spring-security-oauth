package com.baeldung.resource.web.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: gardiary
 * Date: 23/02/22, 12.00
 */
public class CustomClaimsValidatorFilter extends OncePerRequestFilter {
    private static final String EMAIL_DOMAIN = "@baeldung.com";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String preferredUsername = principal.getClaimAsString("preferred_username");

        if(!preferredUsername.endsWith(EMAIL_DOMAIN)) {
            System.out.println("User email domain is not from " + EMAIL_DOMAIN);
            throw new OAuth2AuthenticationException("User unauthorized!");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
