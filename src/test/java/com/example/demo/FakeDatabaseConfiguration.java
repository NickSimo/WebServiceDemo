package com.example.demo;

import com.example.demo.dto.Comuni;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
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

  @Bean(name = "restTemplateMoock")
  public RestTemplate restTemplateMoock() {
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    Comuni[] listaComuni = new Comuni[1];
    Mockito.doAnswer(
            invocationOnMock -> {
              Comuni comune = new Comuni();
              comune.setRegione("Veneto");
              comune.setCap("36010");
              comune.setProvincia("Lonigo");
              comune.setNome("Verona");
              listaComuni[0] = comune;
              ResponseEntity<Comuni[]> response =
                  new ResponseEntity<>(listaComuni, HttpStatus.ACCEPTED);
              return response;
            })
        .when(restTemplate)
        .getForEntity("https://comuni-ita.herokuapp.com/api/comuni", Comuni[].class);
    return restTemplate;
  }
}
