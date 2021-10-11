package com.example.demo.controllers;

import com.example.demo.dto.Cliente;
import com.example.demo.services.ClientiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/clienti")
public class ClientiController {

  @Autowired ClientiService clientiService;

  @GetMapping(value = "/elenco")
  public List<Cliente> cliente() {
    return clientiService.estraiTuttiClienti();
  }

  @PostMapping(value = "/aggiungi")
  public String aggiungiCliente(@RequestBody Cliente cliente) {
    return clientiService.salvaCliente(cliente);
  }

  @GetMapping(value = "/estrazione")
  public Cliente estraiCliente(@RequestParam String nome, @RequestParam String cognome) {
    return clientiService.estraiPerNomeECognome(nome, cognome);
  }
}
