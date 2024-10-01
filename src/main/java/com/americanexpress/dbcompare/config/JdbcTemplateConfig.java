package com.americanexpress.dbcompare.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcTemplateConfig {

    @Bean(name = "jdbcTemplateDatabase1")
    public JdbcTemplate jdbcTemplateDatabase1(@Qualifier("database1DataSource") DataSource database1DataSource) {
        return new JdbcTemplate(database1DataSource);
    }

    @Bean(name = "jdbcTemplateDatabase2")
    public JdbcTemplate jdbcTemplateDatabase2(@Qualifier("database2DataSource") DataSource database2DataSource) {
        return new JdbcTemplate(database2DataSource);
    }
}
