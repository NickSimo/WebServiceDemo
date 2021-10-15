package com.example.demo.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.dto.Cliente;
import com.example.demo.dto.DatiAnagrafici;
import com.example.demo.dto.Normativa;
import com.example.demo.dto.Recapiti;
import java.sql.Date;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class EntrataRepositoryTest {

  @Autowired JdbcTemplate jdbcTemplate;

  private EntrataRepository entrataRepository;

  @AfterEach
  public void after() {
    jdbcTemplate.execute("delete from ingressi");
  }

  @Test
  public void entrata_unClienteEntra_esitoPositivo() {
    Cliente clieneEntrante = inizializzaClienteTest();
    entrataRepository = new EntrataRepository(jdbcTemplate);
    String esito =
        entrataRepository.entra(
            clieneEntrante.getDatiAnagrafici().getCodice_fiscale(),
            clieneEntrante.getDatiAnagrafici().getCognome(),
            Date.valueOf(LocalDate.now()));

    assertThat(esito).isEqualTo("ingresso registrato");
  }

  @Test
  public void entrata_unClienteGiaEntratoEntra_esitoNegativo() {
    Cliente clieneEntrante = inizializzaClienteTest();
    entrataRepository = new EntrataRepository(jdbcTemplate);

    entrataRepository.entra(
        clieneEntrante.getDatiAnagrafici().getCodice_fiscale(),
        clieneEntrante.getDatiAnagrafici().getCognome(),
        Date.valueOf(LocalDate.now()));

    Cliente clieneEntrante2 = inizializzaClienteTest();

    String esito =
        entrataRepository.entra(
            clieneEntrante2.getDatiAnagrafici().getCodice_fiscale(),
            clieneEntrante2.getDatiAnagrafici().getCognome(),
            Date.valueOf(LocalDate.now()));

    int clieneEntrante2Presente =
        jdbcTemplate.queryForObject(
            Query.ricercaEntrata(
                clieneEntrante2.getDatiAnagrafici().getCodice_fiscale(),
                clieneEntrante2.getDatiAnagrafici().getCognome()),
            Integer.class);

    assertThat(esito).isEqualTo("problema nella registrazione dell'ingresso");
    assertThat(clieneEntrante2Presente).isOne();
  }

  @Test
  public void uscita_unClienteEsce_esitoPositivo() {
    Cliente clieneEntrante = inizializzaClienteTest();
    entrataRepository = new EntrataRepository(jdbcTemplate);

    entrataRepository.entra(
        clieneEntrante.getDatiAnagrafici().getCodice_fiscale(),
        clieneEntrante.getDatiAnagrafici().getCognome(),
        Date.valueOf(LocalDate.now()));

    String esito =
        entrataRepository.esce(
            clieneEntrante.getDatiAnagrafici().getCodice_fiscale(),
            clieneEntrante.getDatiAnagrafici().getCognome());

    int utentiPresenti =
        jdbcTemplate.queryForObject(
            Query.ricercaEntrata(
                clieneEntrante.getDatiAnagrafici().getCodice_fiscale(),
                clieneEntrante.getDatiAnagrafici().getCognome()),
            Integer.class);

    assertThat(esito).isEqualTo("ingresso eliminato");
    assertThat(utentiPresenti).isZero();
  }

  @Test
  public void uscita_unClienteEsce_esitoNegativo() {
    Cliente clieneEntrante = inizializzaClienteTest();
    entrataRepository = new EntrataRepository(jdbcTemplate);

    String esito =
        entrataRepository.esce(
            clieneEntrante.getDatiAnagrafici().getCodice_fiscale(),
            clieneEntrante.getDatiAnagrafici().getCognome());

    assertThat(esito).isEqualTo("nessun utente entrato deve uscire");
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
    recapiti.setNazione("it");
    recapiti.setComune_domicilio("montorio");
    recapiti.setIndirizzo_domicilio("erbe");
    recapiti.setIndirizzo_residenza("erbe");
    cliente.setRecapiti(recapiti);
    DatiAnagrafici datiAnagrafici = new DatiAnagrafici();
    datiAnagrafici.setCognome("rossi");
    datiAnagrafici.setNome("mario");
    datiAnagrafici.setCodice_fiscale("RSSMRO12D19L78T");
    datiAnagrafici.setData_nascita(new Date(1997, 7, 10));
    cliente.setDatiAnagrafici(datiAnagrafici);
    return cliente;
  }
}
