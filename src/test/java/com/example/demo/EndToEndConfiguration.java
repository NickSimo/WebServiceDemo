package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class EndToEndConfiguration {

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(new EmbeddedDatabaseBuilder().addScript("test-schema.sql").addScript("test-data.sql").build());
  }
}
