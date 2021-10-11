package com.example.demo.services;

import com.example.demo.dto.Cliente;
import com.example.demo.repositories.ClientiRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientiService {
  private final ClientiRepository clientiRepository;

  public Cliente estraiCliente(String codiceFiscale) {
    return clientiRepository.estraiCliente(codiceFiscale);
  }

  public List<Cliente> estraiTuttiClienti() {
    return clientiRepository.estraiClienti();
  }

  public String salvaCliente(Cliente cliente) {
    return clientiRepository.salvaCliente(cliente);
  }

  public Cliente estraiPerNomeECognome(String nome, String cognome) {
    return clientiRepository.estraiPerNomeECognome(nome, cognome);
  }
}
