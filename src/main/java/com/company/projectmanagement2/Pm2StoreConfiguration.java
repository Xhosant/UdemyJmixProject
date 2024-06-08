package com.company.projectmanagement2;

import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.persistence.DbmsSpecifics;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class Pm2StoreConfiguration {

    @Bean
    @ConfigurationProperties("pm2.datasource")
    DataSourceProperties pm2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix="pm2.datasource.hikari")
    DataSource pm2DataSource(@Qualifier("pm2DataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean pm2EntityManagerFactory(
            @Qualifier("pm2DataSource") DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter,
            DbmsSpecifics dbmsSpecifics,
            JmixModules jmixModules,
            Resources resources
    ) {
        return new JmixEntityManagerFactoryBean("pm2", dataSource, jpaVendorAdapter, dbmsSpecifics, jmixModules, resources);
    }

    @Bean
    JpaTransactionManager pm2TransactionManager(@Qualifier("pm2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("pm2", entityManagerFactory);
    }
}
