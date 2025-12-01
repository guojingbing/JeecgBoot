package org.jeecg.modules.zxecg.phoenix.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    @Value("${spring.datasource.phoenix.schema}")
    private String schema;

    @Bean(name="phoenixJdbcTemplate")
    public JdbcTemplate phoenixJdbcTemplate(@Qualifier("phoenixDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        String sql=("create schema if not exists "+schema).toUpperCase();
        jdbcTemplate.execute(sql);
        return jdbcTemplate;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
