package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FakeDatabaseConfiguration {

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(
        new EmbeddedDatabaseBuilder()
            .addScript("test-schema.sql")
            .addScript("test-data.sql")
            .build());
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }


}
