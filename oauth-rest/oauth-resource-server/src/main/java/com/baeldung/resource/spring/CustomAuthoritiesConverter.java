package com.baeldung.resource.spring;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import static com.baeldung.resource.web.filter.CustomClaimsValidatorFilter.EMAIL_DOMAIN;

public class CustomAuthoritiesConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {
        String preferredUsername = jwt.getClaimAsString("preferred_username");
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);

        if(preferredUsername.endsWith(EMAIL_DOMAIN)) {
            System.out.println("User email is from " + EMAIL_DOMAIN + ", set as superuser");
            authorities.add(new SimpleGrantedAuthority("SCOPE_superuser"));
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }

    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return this.jwtGrantedAuthoritiesConverter.convert(jwt);
    }

}