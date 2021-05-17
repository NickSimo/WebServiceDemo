package com.example.demo.controller;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.DatiIngresso;
import com.example.demo.service.EstraiClientiService;
import com.example.demo.service.InserimentoNuovoIngressoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/clienti")
public class ClientiController {

    private final EstraiClientiService estraiClientiService;
    private final InserimentoNuovoIngressoService inserimentoNuovoIngressoService;

    @GetMapping(value = "/elenco")
    public List<Cliente> clienti() {
        return estraiClientiService.estraiTuttiIClienti();
    }

    @PostMapping(value = "/ingressi/nuovo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String inserimentoNuovoIngresso(@RequestBody DatiIngresso datiIngresso) {
        return inserimentoNuovoIngressoService.inserimentoNuovoIngresso(datiIngresso.getCodiceFiscale(), datiIngresso.getNominativo());
    }


}
