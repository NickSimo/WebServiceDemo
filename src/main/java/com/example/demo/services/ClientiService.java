package com.example.demo.services;

import com.example.demo.dto.Cliente;
import com.example.demo.repositories.ClientiRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientiService {
  @Autowired ClientiRepository clientiRepository;

  public Cliente estraiCliente(String codiceFisclae) {
    return clientiRepository.estraiCliente(codiceFisclae);
  }

  public List<Cliente> estraiTuttiClienti(){
    return clientiRepository.estraiClienti();
  }
}
