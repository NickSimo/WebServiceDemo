package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.demo.dto.Cliente;
import com.example.demo.dto.DatiAnagrafici;
import com.example.demo.dto.Normativa;
import com.example.demo.dto.Recapiti;
import java.sql.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

  @Test
  public void inserimentoCliente_conUrlCorretto_LaRispostaEOk() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(inizializzaClienteTest())))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("Il Cliente è stato inserito correttamente", message);

    int numeroDiRecordInseriti =
        jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM clienti WHERE codice_fiscale = 'RSSMRO12D19L799'", Integer.class);
    assertEquals(1, numeroDiRecordInseriti);
  }

  @Test
  public void inserimentoCliente_conUrlScorretto_LaRispostaEKo() throws Exception {

    mockMvc
        .perform(
            post("/clientis/aggiungi")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonComposer.getInputJson(inizializzaClienteTest())))
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  public void estrazionePerNomeECognome_conUrlCorretto_LarispotaEOk() throws Exception {
    mockMvc
        .perform(
            get("/clienti/estrazione")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("nome", "Ciro")
                .param("cognome", "Esposito"))
        .andExpect(status().isOk());
  }

  @Test
  public void estrazionePerNomeECognome_conUrlCorretto_LarispotaEKo() throws Exception {
    mockMvc
        .perform(
            get("/clientis/estrazione")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("nome", "Ciro")
                .param("cognome", "Esposito"))
        .andExpect(status().isNotFound());
  }

  private Cliente inizializzaClienteTest() {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    Normativa normativa = new Normativa();
    normativa.setNumero_carta_identita("AX0001");
    cliente.setNormativa(normativa);
    Recapiti recapiti = new Recapiti();
    recapiti.setComune_residenza("Verona");
    recapiti.setFl_domicilio_estero(false);
    recapiti.setNazione("IT");
    recapiti.setComune_domicilio("Montorio");
    recapiti.setIndirizzo_domicilio("via delle erbe");
    recapiti.setIndirizzo_residenza("via delle erbe");
    cliente.setRecapiti(recapiti);
    DatiAnagrafici datiAnagrafici = new DatiAnagrafici();
    datiAnagrafici.setCognome("Rossi");
    datiAnagrafici.setNome("mario");
    datiAnagrafici.setCodice_fiscale("RSSMRO12D19L799");
    datiAnagrafici.setData_nascita(new Date(1997, 7, 10));
    cliente.setDatiAnagrafici(datiAnagrafici);
    return cliente;
  }
}
