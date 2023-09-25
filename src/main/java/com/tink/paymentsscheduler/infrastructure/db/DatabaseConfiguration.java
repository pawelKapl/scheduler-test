package com.tink.paymentsscheduler.infrastructure.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource.scheduler.hikari")
    DataSource getWriterDataSource(@Qualifier("schedulerDatasourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    @Bean(name = "schedulerDatasourceProperties")
    @ConfigurationProperties("spring.datasource.scheduler")
    public DataSourceProperties writerMySqlProperties() {
        return new DataSourceProperties();
    }
}
