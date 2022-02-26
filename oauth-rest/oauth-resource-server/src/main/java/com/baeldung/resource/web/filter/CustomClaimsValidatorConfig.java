package com.baeldung.resource.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CustomClaimsValidatorConfig {
    private static final String SLASH_ALL = "/*";

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final var customClaimsValidatorFilter = new CustomClaimsValidatorFilter();
        final var filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(customClaimsValidatorFilter);
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.setUrlPatterns(Arrays.asList(
                "/api/foos" + SLASH_ALL,
                "/api/secured/foos" + SLASH_ALL
        ));
        return filterRegistrationBean;
    }
}
