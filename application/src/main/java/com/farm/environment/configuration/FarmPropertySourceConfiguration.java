package com.farm.environment.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource(value = "classpath:database/hibernate.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/application.properties", ignoreResourceNotFound = true)
public class FarmPropertySourceConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "/database/accountDescriptions",
                "/database/accountDescriptions",
                "/database/operationTypeDescriptions"
        );
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
