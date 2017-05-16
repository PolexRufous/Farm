package com.farm.environment.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@PropertySource(value = "classpath:database/hibernate.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/project_constants.properties", ignoreResourceNotFound = true)
public class FarmPropertySourceConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource =
                new ResourceBundleMessageSource();
        messageSource.setBasenames(
                "/database/accountDescriptions",
                "/database/operationTypeDescriptions",
                "config/project_constants"
        );
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
