package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.demo.dto.Cliente;
import com.example.demo.dto.DatiAnagrafici;
import com.example.demo.dto.Normativa;
import com.example.demo.dto.Recapiti;
import com.example.demo.mappers.ClientiRowMapper;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
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
public class ClientiControllerE2E {

  @Autowired private MockMvc mockMvc;

  @Autowired JdbcTemplate jdbcTemplate;

  @AfterEach
  public void after() {
    jdbcTemplate.execute("delete from clienti");
  }

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
            "SELECT COUNT(*) FROM clienti WHERE codice_fiscale = 'RSSMRO12D19L799Q'",
            Integer.class);
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

  @Test
  public void inserimentoCliente_conClienteNonValidoPerCodiceFiscale_vieneSegnalatoErrore()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCodice_fiscale("1234");
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("ERROR codice fiscale di lunghezza inferiore di quello standard : 1234", message);

    int numeroDiRecordInseriti =
        jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM clienti WHERE codice_fiscale = 'RSSMRO12D19L799'", Integer.class);
    assertEquals(0, numeroDiRecordInseriti);
  }

  @Test
  public void inserimentoCliente_conClienteNonValidoPerNominativo_vieneSegnalatoErrore()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCognome("");
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("ERROR cognome assente ", message);

    int numeroDiRecordInseriti =
        jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM clienti WHERE codice_fiscale = 'RSSMRO12D19L799'", Integer.class);
    assertEquals(0, numeroDiRecordInseriti);
  }

  @Test
  public void inserimentoCliente_conClienteNonValidoPerDataDiNascita_vieneSegnalatoErrore()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setData_nascita(Date.valueOf("2004-01-01"));
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("ERROR utente minorenne : 2004-01-01", message);

    int numeroDiRecordInseriti =
        jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM clienti WHERE codice_fiscale = 'RSSMRO12D19L799'", Integer.class);
    assertEquals(0, numeroDiRecordInseriti);
  }

  @Test
  public void inserimentoCliente_conClienteCodiceFiscaleNumerico_vieneSegnalatoWarning()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCodice_fiscale("1234567890123456");
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("WARNING codice fiscale solo numerico : 1234567890123456", message);
  }

  @Test
  public void inserimentoCliente_conClienteCodiceFiscaleLettere_vieneSegnalatoWarning()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCodice_fiscale("qwertyuiolkjhgfd");
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("WARNING codice fiscale solo di lettere : qwertyuiolkjhgfd", message);
  }

  @Test
  public void inserimentoCliente_conCognomeInCaps_vieneSegnalatoWarning() throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCognome("ROSSI");
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("WARNING cognome in maiuscolo: ROSSI", message);
  }

  @Test
  public void inserimentoCliente_conNomeInCaps_vieneSegnalatoWarning() throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setNome("MARIO");
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/aggiungi")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    String message = result.getResponse().getContentAsString();
    assertEquals("WARNING nome in maiuscolo: MARIO", message);
  }

  @Test
  public void iaggiornamentoAnagrafica_lAnagraficaDelClienteVieneAggiornata_laRispostaEOk()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    mockMvc
        .perform(
            post("/clienti/aggiungi")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonComposer.getInputJson(cliente)))
        .andExpect(status().isOk())
        .andReturn();
    Recapiti recapiti = inizializzaRecapitoTest();

    mockMvc
        .perform(
            post("/clienti/aggiornaRecapito")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonComposer.getInputJson(recapiti))
                .param("codiceFiscale", cliente.getDatiAnagrafici().getCodice_fiscale()))
        .andExpect(status().isOk())
        .andReturn();

    Cliente clienteEstratto =
        jdbcTemplate.queryForObject(
            "SELECT * FROM clienti WHERE codice_fiscale = 'RSSMRO12D19L799Q'",
            new ClientiRowMapper());

    assertEquals(clienteEstratto.getRecapiti().getComune_residenza(), "Sirmione");
  }

  @Test
  public void aggiornamentoAnagrafica_lAnagraficaDelClienteNonVieneAggiornata_laRispostaEOk()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    mockMvc
        .perform(
            post("/clienti/aggiungi")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonComposer.getInputJson(cliente)))
        .andExpect(status().isOk())
        .andReturn();
    Recapiti recapiti = inizializzaRecapitoTest();

    mockMvc
        .perform(
            post("/clienti/aggiornaRecapito")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonComposer.getInputJson(recapiti))
                .param("codiceFiscale", "RSSMRO12D19L799W"))
        .andExpect(status().isOk())
        .andReturn();

    Cliente clienteEstratto =
        jdbcTemplate.queryForObject(
            "SELECT * FROM clienti WHERE codice_fiscale = 'RSSMRO12D19L799Q'",
            new ClientiRowMapper());

    assertEquals(clienteEstratto.getRecapiti().getComune_residenza(), "Verona");
  }

  @Test
  public void isMaggiorenne_lUtenteEMaggiorenne_laRispostaEOk()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    mockMvc
        .perform(
            post("/clienti/aggiungi")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonComposer.getInputJson(cliente)))
        .andExpect(status().isOk())
        .andReturn();
    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/isMaggiorenne")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(result.getResponse().getContentAsString(), "e maggiorenne");
  }

  @Test
  public void isMaggiorenne_lUtenteEMinorenne_laRispostaEOk()
      throws Exception {

    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setData_nascita(Date.valueOf("2008-01-01"));

    mockMvc
        .perform(
            post("/clienti/aggiungi")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonComposer.getInputJson(cliente)))
        .andExpect(status().isOk())
        .andReturn();

    MvcResult result =
        mockMvc
            .perform(
                post("/clienti/isMaggiorenne")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonComposer.getInputJson(cliente)))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(result.getResponse().getContentAsString(), "e minorenne");
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
    datiAnagrafici.setCodice_fiscale("RSSMRO12D19L799Q");
    datiAnagrafici.setData_nascita(Date.valueOf("1997-07-10"));
    cliente.setDatiAnagrafici(datiAnagrafici);
    return cliente;
  }

  private Recapiti inizializzaRecapitoTest() {
    Recapiti recapiti = new Recapiti();
    recapiti.setComune_residenza("Sirmione");
    recapiti.setFl_domicilio_estero(false);
    recapiti.setNazione("it");
    recapiti.setComune_domicilio("Lazise");
    recapiti.setIndirizzo_domicilio("acque");
    recapiti.setIndirizzo_residenza("acque");
    return recapiti;
  }
}
