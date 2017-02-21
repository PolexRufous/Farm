package com.farm.environment.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySources({
        @PropertySource("classpath:database/hibernate.properties"),
        @PropertySource("classpath:config/application.properties")
})
public class FarmPropertySource {

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
