package com.example.demo.services;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



import com.example.demo.dto.Cliente;
import com.example.demo.dto.DatiAnagrafici;
import com.example.demo.dto.Normativa;
import com.example.demo.dto.Recapiti;
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
    when(clientiRepository.estraiCliente("RSSMRO12D19L78T")).thenReturn(inizializzaClienteTest());
    Cliente clienti = clientiRepository.estraiCliente("RSSMRO12D19L78T");
    assertThat(clienti.getId()).isEqualTo(1);
  }

  @Test
  public void salvaCliente_ilClienteVieneAggiunto() {
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
    datiAnagrafici.setCodice_fiscale("RSSMRO12D19L78T");
    datiAnagrafici.setData_nascita(new Date(1997, 7, 10));
    cliente.setDatiAnagrafici(datiAnagrafici);
    return cliente;
  }

  private List<Cliente> inizializzaLista() {
    List<Cliente> clienti = new ArrayList<>();
    clienti.add(inizializzaClienteTest());
    return clienti;
  }
}
