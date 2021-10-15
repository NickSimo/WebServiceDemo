package com.example.demo.controllers;

import com.example.demo.dto.Cliente;
import com.example.demo.dto.Comuni;
import com.example.demo.services.ComuniAdapterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comuni")
public class ComuniController {

  @Autowired ComuniAdapterService comuniAdapterService;

  @GetMapping(value = "/elenco")
  @Transactional()
  public List<Comuni> comuni() throws Exception {
    return comuniAdapterService.chiama();
  }
}
