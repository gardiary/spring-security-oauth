package com.baeldung.resource.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * User: gardiary
 * Date: 23/02/22, 12.22
 */
@Configuration
public class CustomClaimsValidatorConfig {
    private static final String SLASH_ALL = "/*";

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final var customClaimsValidatorFilter = new CustomClaimsValidatorFilter();
        final var filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(customClaimsValidatorFilter);
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.setUrlPatterns(Arrays.asList(
                "/api/foos" + SLASH_ALL
        ));
        return filterRegistrationBean;
    }
}
