package com.example.demo.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.dto.Cliente;
import com.example.demo.dto.DatiAnagrafici;
import com.example.demo.dto.Normativa;
import com.example.demo.dto.Recapiti;
import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class ClientiRepositoryTest {

  private ClientiRepository clientiRepository;

  @Autowired JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void before() {
    clientiRepository = new ClientiRepository(jdbcTemplate);
  }

  @Test
  public void cliente_vieneEstrattoUnCliente_conIParametriDiInput() {

    Cliente cliente = clientiRepository.estraiCliente("RSSMRO12D19L78T");
    assertThat(cliente.getDatiAnagrafici().getNome()).isEqualTo("Mario");
  }

  @Test
  public void cliente_nonVieneEstrattoUnCliente() {
    Cliente cliente = clientiRepository.estraiCliente("RSSMRO12D19L78K");
    assertThat(cliente).isNull();
  }

  @Test
  public void cliente_estrattiTuttiIClienti() {
    List<Cliente> clienti = clientiRepository.estraiClienti();
    assertThat(clienti.size()).isGreaterThan(0);
  }

  @Test
  public void inserimento_nuovoClienteSuccesso() {
    Cliente cliente = inizializzaClienteTest();
    String esito = clientiRepository.salvaCliente(cliente);
    assertThat(esito).isEqualTo("Il Cliente è stato inserito correttamente");
  }

  @Test
  public void inserimento_nuovoClienteSenzaSuccesso() {
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCodice_fiscale("qwertyuioplkjhgfdssdsdsdsdsdsfd");
    String esito = clientiRepository.salvaCliente(cliente);
    assertThat(esito).isEqualTo("errore");
  }

  @Test
  public void estrazionePerNomeECognome_ritornoCodiceFiscale(){
    Cliente cliente = clientiRepository.estraiPerNomeECognome("Ciro","Esposito");
    assertThat(cliente.getDatiAnagrafici().getCodice_fiscale()).isEqualTo("CROSPS12D19L78T ");
  }

  @Test
  public void estrazionePerNomeECognome_nonRitornoCodiceFiscale(){
    Cliente cliente = clientiRepository.estraiPerNomeECognome("Marco","Esposito");
    assertThat(cliente).isNull();
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
