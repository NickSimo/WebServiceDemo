package com.example.demo.controllers;

import com.example.demo.dto.Cliente;
import com.example.demo.services.EntrateService;
import java.sql.Date;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/entrate")
public class EntrateController {

  @Autowired EntrateService entrateService;

  @PostMapping(value = "/entra")
  public String agginungiEntrata(@RequestBody Cliente cliente) {
    return entrateService.entra(
        cliente.getDatiAnagrafici().getCodice_fiscale(),
        cliente.getDatiAnagrafici().getCognome(),
        Date.valueOf(LocalDate.now()));
  }

  @PostMapping(value = "/esci")
  public String rimuoviEntrata(@RequestBody Cliente cliente) {
    return entrateService.esce(
        cliente.getDatiAnagrafici().getCodice_fiscale(), cliente.getDatiAnagrafici().getCognome());
  }
}
