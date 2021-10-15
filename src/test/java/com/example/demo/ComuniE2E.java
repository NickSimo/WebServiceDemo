package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FakeDatabaseConfiguration.class)
public class ComuniE2E {

  @Autowired private MockMvc mockMvc;

  @Test
  public void estrazioneCliente_conUrlCorretto_laRispostaEOk() throws Exception {
    mockMvc.perform(get("/comuni/elenco")).andExpect(status().isOk());
  }

  @Test
  public void estrazioneCliente_conUrlNonCorretto_laRispostaENotFound() throws Exception {
    mockMvc.perform(get("/comuni/elenco2")).andExpect(status().isNotFound());
  }
}
