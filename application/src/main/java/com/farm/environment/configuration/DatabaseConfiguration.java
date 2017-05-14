package com.farm.environment.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "com.farm.database"
})
@EnableAutoConfiguration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DatabaseConfiguration {

    private Environment environment;

    @SuppressWarnings("ConstantConditions")
    @Bean
    @LiquibaseDataSource
    public DataSource dataSource() {
        String hikariPropertiesFilePath = "/database/hikari.properties";
        ClassPathResource configFile = new ClassPathResource(hikariPropertiesFilePath);
        if (configFile.exists()) {
            HikariConfig config = new HikariConfig(hikariPropertiesFilePath);
            return new HikariDataSource(config);
        }

        return getHikariDataSourceFromArguments();
    }

    private HikariDataSource getHikariDataSourceFromArguments() {
        HikariDataSource dataSource = new HikariDataSource();
        String userName = getProperty("dataSource.user");
        String password = getProperty("dataSource.password");
        String dataBaseName = getProperty("dataSource.databaseName");
        String portNumber = getProperty("dataSource.portNumber");
        String serverAddress = getProperty("dataSource.serverName");

        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:postgresql://" + serverAddress + ":" +portNumber + "/" + dataBaseName);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(getProperty("package.to.scan.entities"));

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private String getProperty(String key) {
        return environment.getProperty(key);
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.dialect", getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.generate_statistics", getProperty("hibernate.generate_statistics"));
        properties.setProperty("hibernate.connection.pool_size", getProperty("hibernate.connection.pool_size"));

        return properties;
    }
}
