package com.example.demo.controller;

import com.example.demo.entity.Cliente;
import com.example.demo.service.EstraiClientiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientiController {

  private final EstraiClientiService estraiClientiService;

  public ClientiController(EstraiClientiService estraiClientiService) {
    this.estraiClientiService = estraiClientiService;
  }

  @GetMapping(value = "/clienti")
  public @ResponseBody
  List<Cliente> clienti() {
    return estraiClientiService.estraiTuttiIClienti();
  }


}
