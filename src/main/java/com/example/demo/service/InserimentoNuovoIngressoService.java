package com.example.demo.service;

import com.example.demo.entity.DatiIngresso;
import com.example.demo.repository.IngressoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.utility.Dates.today;

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
