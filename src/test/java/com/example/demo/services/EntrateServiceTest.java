package com.example.demo.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.demo.adapter.ComuniRestAdapter;
import com.example.demo.dto.Cliente;
import com.example.demo.dto.Comuni;
import com.example.demo.dto.DatiAnagrafici;
import com.example.demo.dto.Normativa;
import com.example.demo.dto.Recapiti;
import com.example.demo.repositories.ClientiRepository;
import com.example.demo.repositories.EntrataRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EntrateServiceTest {

  private EntrataRepository entrataRepository = Mockito.mock(EntrataRepository.class);
  private ComuniRestAdapter comuniRestAdapter = Mockito.mock(ComuniRestAdapter.class);
  private ClientiRepository clientiRepository = Mockito.mock(ClientiRepository.class);

  @Test
  public void entra_ilMetodoEntraVieneCorrettamenteChiamato() {
    EntrateService entrateService =
        new EntrateService(entrataRepository, comuniRestAdapter, clientiRepository);
    when(entrataRepository.entra(eq("MRSSWERTYGDL"), eq("rossi"), any(Date.class)))
        .thenReturn("success");
    String esito = entrateService.entra("MRSSWERTYGDL", "rossi", Date.valueOf(LocalDate.now()));
    assertThat(esito).isEqualTo("success");
  }

  @Test
  public void entra_ilMetodoEntraNonVieneCorrettamenteChiamato() {
    EntrateService entrateService =
        new EntrateService(entrataRepository, comuniRestAdapter, clientiRepository);
    when(entrataRepository.entra(eq("MRSSWERTYGDL"), eq("rossi"), any(Date.class)))
        .thenReturn("success");
    String esito = entrateService.entra("MRSSWERTYGDL", "bianchi", Date.valueOf(LocalDate.now()));
    assertThat(esito).isNotEqualTo("success");
  }

  @Test
  public void esce_ilMetodoEsceVieneCorrettamenteChiamato() {
    EntrateService entrateService =
        new EntrateService(entrataRepository, comuniRestAdapter, clientiRepository);
    when(entrataRepository.esce(eq("MRSSWERTYGDL"), eq("rossi"))).thenReturn("success");
    String esito = entrateService.esce("MRSSWERTYGDL", "rossi");
    assertThat(esito).isEqualTo("success");
  }

  @Test
  public void esce_ilMetodoEsceNonVieneCorrettamenteChiamato() {
    EntrateService entrateService =
        new EntrateService(entrataRepository, comuniRestAdapter, clientiRepository);
    when(entrataRepository.esce(eq("MRSSWERTYGDL"), eq("rossi"))).thenReturn("success");
    String esito = entrateService.esce("MRSSWERTYGDL", "bianchi");
    assertThat(esito).isNotEqualTo("success");
  }

  @Test
  public void entra_clienteConComunePresenteInLista() {
    EntrateService entrateService =
        new EntrateService(entrataRepository, comuniRestAdapter, clientiRepository);
    when(clientiRepository.estraiCliente(eq("MRSSWERTYGDL"))).thenReturn(inizializzaClienteTest());
    when(comuniRestAdapter.chiama()).thenReturn(comuniList());
    when(entrataRepository.entra(eq("MRSSWERTYGDL"), eq("rossi"), any(Date.class)))
        .thenReturn("success");
    String esito = entrateService.entra("MRSSWERTYGDL", "rossi", Date.valueOf(LocalDate.now()));
    assertThat(esito).isEqualTo("success");
  }

  @Test
  public void entra_clienteConComuneNonPresenteInLista() {
    EntrateService entrateService =
        new EntrateService(entrataRepository, comuniRestAdapter, clientiRepository);
    Cliente cliente = inizializzaClienteTest();
    cliente.getRecapiti().setComune_residenza("Topolonia");
    when(clientiRepository.estraiCliente(eq("MRSSWERTYGDL"))).thenReturn(cliente);
    when(comuniRestAdapter.chiama()).thenReturn(comuniList());
    when(entrataRepository.entra(eq("MRSSWERTYGDL"), eq("rossi"), any(Date.class)))
        .thenReturn("success");
    String esito = entrateService.entra("MRSSWERTYGDL", "rossi", Date.valueOf(LocalDate.now()));
    assertThat(esito).isEqualTo("il comune Topolonia non è presente nella lista");
  }

  private List<Comuni> comuniList() {
    List<Comuni> comuniList = new ArrayList<>();
    Comuni comune = new Comuni();
    comune.setNome("Sirmione");
    comuniList.add(comune);
    return comuniList;
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
    recapiti.setComune_domicilio("è q");
    recapiti.setIndirizzo_domicilio("via delle erbe");
    recapiti.setIndirizzo_residenza("via delle erbe");
    cliente.setRecapiti(recapiti);
    DatiAnagrafici datiAnagrafici = new DatiAnagrafici();
    datiAnagrafici.setCognome("Rossi");
    datiAnagrafici.setNome("mario");
    datiAnagrafici.setCodice_fiscale("RSSMRO12D19L78TQ");
    datiAnagrafici.setData_nascita(Date.valueOf("1997-07-10"));
    cliente.setDatiAnagrafici(datiAnagrafici);
    cliente.setRecapiti(inizializzaRecapitoTest());
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
