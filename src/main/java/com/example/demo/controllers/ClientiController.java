package com.example.demo.controllers;

import com.example.demo.dto.Cliente;
import com.example.demo.services.ClientiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
