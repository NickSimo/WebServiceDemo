package com.example.demo.services;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.demo.dto.Cliente;
import com.example.demo.dto.DatiAnagrafici;
import com.example.demo.dto.Normativa;
import com.example.demo.dto.Recapiti;
import com.example.demo.exceptions.ValidazioneException;
import com.example.demo.repositories.ClientiRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ClientiServiceTest {

  private ClientiRepository clientiRepository = Mockito.mock(ClientiRepository.class);

  @Test
  public void estrazioneClienti_estrazioneAvvenutaConSuccesso() {
    when(clientiRepository.estraiClienti()).thenReturn(inizializzaLista());
    List<Cliente> clienti = clientiRepository.estraiClienti();
    assertThat(clienti.size()).isGreaterThan(0);
  }

  @Test
  public void estrazioneCliente_estrazioneAvvenutaConSuccesso() {
    when(clientiRepository.estraiCliente("RSSMRO12D19L78TQ")).thenReturn(inizializzaClienteTest());
    Cliente clienti = clientiRepository.estraiCliente("RSSMRO12D19L78TQ");
    assertThat(clienti.getId()).isEqualTo(1);
  }

  @Test
  public void salvaCliente_ilClienteVieneAggiunto() throws Exception {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(any())).thenReturn("success");
    String risposta = clientiService.salvaCliente(inizializzaClienteTest());
    assertThat(risposta).isEqualTo("success");
  }

  @Test
  public void salvaCliente_ilClienteVieneNonAggiunto() {
    when(clientiRepository.salvaCliente(any())).thenReturn("error");
    String risposta = clientiRepository.salvaCliente(inizializzaClienteTest());
    assertThat(risposta).isEqualTo("error");
  }

  @Test
  public void estraiClientePerNomeECognome_vieneEstrattoIlCliente() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.estraiPerNomeECognome("mario", "rossi"))
        .thenReturn(inizializzaClienteTest());
    Cliente clienteEstratto = clientiService.estraiPerNomeECognome("mario", "rossi");
    assertThat(clienteEstratto).isNotNull();
  }

  @Test
  public void estraiClientePerNomeECognome_nonVieneEstrattoIlCliente() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.estraiPerNomeECognome("mario", "rossi"))
        .thenReturn(inizializzaClienteTest());
    Cliente clienteEstratto = clientiService.estraiPerNomeECognome("pippo", "pluto");
    assertThat(clienteEstratto).isNull();
  }

  @Test
  public void salva_codiceFiscaleNonValido_ilMetodoLanciaEccezione() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCodice_fiscale("qwe");
    assertThrows(ValidazioneException.class, () -> clientiService.salvaCliente(cliente));
  }

  @Test
  public void salva_nominativoNonValido_ilMetodoLanciaEccezione() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCognome("");
    assertThrows(ValidazioneException.class, () -> clientiService.salvaCliente(cliente));
  }

  @Test
  public void salva_minorenne_ilMetodoLanciaEccezione() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setData_nascita(Date.valueOf("2004-07-10"));
    assertThrows(ValidazioneException.class, () -> clientiService.salvaCliente(cliente));
  }

  @Test
  public void salva_codFiscaleSoloDiNumeri_ilMetodoLanciaEccezione() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCodice_fiscale("1234567890123456");
    assertThrows(ValidazioneException.class, () -> clientiService.salvaCliente(cliente));
  }

  @Test
  public void salva_codFiscaleSoloDiLettere_ilMetodoLanciaEccezione() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCodice_fiscale("qwertyuioplkjhgf");
    assertThrows(ValidazioneException.class, () -> clientiService.salvaCliente(cliente));
  }

  @Test
  public void salva_nominativoInCapsLock_ilMetodoLanciaEccezione() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setCognome("ROSSI");
    assertThrows(ValidazioneException.class, () -> clientiService.salvaCliente(cliente));
  }

  @Test
  public void salva_nomeInCapsLock_ilMetodoLanciaEccezione() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setNome("MARIO");
    assertThrows(ValidazioneException.class, () -> clientiService.salvaCliente(cliente));
  }

  @Test
  public void aggiornaRecapito_vieneAggiornatoIlRecapito_ilMetodoVieneChiamato() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    when(clientiRepository.aggiornaRecapito(
            inizializzaRecapitoTest(),
            inizializzaClienteTest().getDatiAnagrafici().getCodice_fiscale()))
        .thenReturn("aggiornamento effettuato con successo");

    String esito =
        clientiService.aggiornaRecapito(
            inizializzaRecapitoTest(),
            inizializzaClienteTest().getDatiAnagrafici().getCodice_fiscale());

    assertEquals(esito, "aggiornamento effettuato con successo");
  }

  @Test
  public void aggiornaRecapito_NonvieneAggiornatoIlRecapito_ilMetodoRitornaErrore() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.salvaCliente(inizializzaClienteTest())).thenReturn("success");
    when(clientiRepository.aggiornaRecapito(
            inizializzaRecapitoTest(),
            inizializzaClienteTest().getDatiAnagrafici().getCodice_fiscale()))
        .thenReturn("aggiornamento effettuato con successo");

    String esito = clientiService.aggiornaRecapito(inizializzaRecapitoTest(), "QWERFDSAZXCVB123");

    assertNotEquals(esito, "aggiornamento non riuscito");
  }

  @Test
  public void estrazioneAnagrafica_estrazioneAvvenutaConSuccesso() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.estraiCliente("RSSMRO12D19L78TQ")).thenReturn(inizializzaClienteTest());
    String anagrafica = clientiService.estraiAnagrafica("RSSMRO12D19L78TQ");
    assertThat(anagrafica).isEqualTo("il cliente si chiama mario Rossi");
  }

  @Test
  public void estrazioneAnagrafica_estrazioneNonAvvenuta() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.estraiCliente("RSSMRO12D19L78TQ")).thenReturn(inizializzaClienteTest());
    String anagrafica = clientiService.estraiAnagrafica("RSSMRO12D19L78TT");
    assertThat(anagrafica).isEqualTo("errore");
  }

  @Test
  public void isMaggiorenne_utenteMaggiorenne() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    when(clientiRepository.estraiCliente("RSSMRO12D19L78TQ")).thenReturn(inizializzaClienteTest());
    String esito = clientiService.isMaggiorenne("RSSMRO12D19L78TQ");
    assertThat(esito).isEqualTo("e maggiorenne");
  }

  @Test
  public void isMaggiorenne_utenteMinorenne() {
    ClientiService clientiService = new ClientiService(clientiRepository);
    Cliente cliente = inizializzaClienteTest();
    cliente.getDatiAnagrafici().setData_nascita(Date.valueOf("2008-01-01"));
    when(clientiRepository.estraiCliente("RSSMRO12D19L78TQ")).thenReturn(cliente);
    String esito = clientiService.isMaggiorenne("RSSMRO12D19L78TQ");
    assertThat(esito).isEqualTo("e minorenne");
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
    datiAnagrafici.setCodice_fiscale("RSSMRO12D19L78TQ");
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

  private List<Cliente> inizializzaLista() {
    List<Cliente> clienti = new ArrayList<>();
    clienti.add(inizializzaClienteTest());
    return clienti;
  }
}
