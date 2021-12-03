package com.example.demo.controller;

import com.example.demo.entity.Cliente;
import com.example.demo.service.EstraiClientiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/clienti")
public class ClientiController {

    private final EstraiClientiService estraiClientiService;

    @GetMapping(value = "/elenco")
    public List<Cliente> clienti() {
        return estraiClientiService.estraiTuttiIClienti();
    }

    @GetMapping(value = "/estrazione-per-cf")
    public Cliente clienti(@RequestParam String cf) {
        return estraiClientiService.estrazioneClientePerCodiceFiscale(cf);
    }

    @GetMapping(value = "test/{numero}")
    public int clienti(@PathVariable int numero) {
        return numero;
    }


}
