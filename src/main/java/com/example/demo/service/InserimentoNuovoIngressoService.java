package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.DatiIngresso;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.IngressoRepository;
import com.example.demo.service.utility.Dates;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.example.demo.service.utility.Dates.today;

@Service
@RequiredArgsConstructor
public class InserimentoNuovoIngressoService {
    private final IngressoRepository ingressoRepository;

    public String inserimentoNuovoIngresso(String codiceFiscale, String nominativo) {
        DatiIngresso datiIngresso = ingressoRepository.estrazioneIngressoGiornaliero(codiceFiscale);
        if (datiIngresso == null) {
            ingressoRepository.inserimentoNuovoIngresso(codiceFiscale, nominativo, today());
            return "Ingresso per " + nominativo + " avvenuto correttamente";
        } else {
            return "Ingresso gia avvenuto in data odierna";
        }
    }
}
