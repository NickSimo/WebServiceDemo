package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FakeDatabaseConfiguration.class)
public class DemoApplicationE2E {

  @Autowired private MockMvc mockMvc;

  @Autowired JdbcTemplate jdbcTemplate;

  @Test
  public void estrazioneCliente_conUrlCorretto_laRispostaEOk() throws Exception {
    mockMvc.perform(get("/clienti/elenco")).andExpect(status().isOk());
  }

  @Test
  public void estrazioneCliente_conUrlScorretto_LaRispostaEKo() throws Exception {
    mockMvc.perform(get("/clientis/elenco")).andExpect(status().isNotFound());
  }
}
