package com.example.demo.services;

import com.example.demo.adapter.ComuniRestAdapter;
import com.example.demo.dto.Cliente;
import com.example.demo.dto.Comuni;
import com.example.demo.repositories.ClientiRepository;
import com.example.demo.repositories.EntrataRepository;
import java.sql.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntrateService {

  private final EntrataRepository entrataRepository;
  private final ComuniRestAdapter comuniRestAdapter;
  private final ClientiRepository clientiRepository;

  public String entra(String codiceFiscale, String cognome, Date dateEntrata) {
    List<Comuni> comunis = comuniRestAdapter.chiama();
    Cliente clienteEntrante = clientiRepository.estraiCliente(codiceFiscale);
    for (Comuni comune : comunis) {
      if (clienteEntrante.getRecapiti().getComune_residenza().equals(comune.getNome())) {
        return entrataRepository.entra(codiceFiscale, cognome, dateEntrata);
      }
    }
    return "il comune "
        .concat(clienteEntrante.getRecapiti().getComune_residenza())
        .concat(" non è presente nella lista");
  }

  public String esce(String codiceFiscale, String cognome) {
    return entrataRepository.esce(codiceFiscale, cognome);
  }
}
