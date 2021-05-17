package com.example.demo.service;

import com.example.demo.entity.DatiIngresso;
import com.example.demo.repository.IngressoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class InserimentoNuovoIngressoServiceTest {

    private IngressoRepository ingressoRepository = Mockito.mock(IngressoRepository.class);
    private InserimentoNuovoIngressoService inserimentoNuovoIngressoService = new InserimentoNuovoIngressoService(ingressoRepository);

    @Test
    public void inserimentoNuovoIngresso_InserimentoAvvenuto_Test() {

        when(ingressoRepository.estrazioneIngressoGiornaliero(anyString())).thenReturn(null);

        String messaggio = inserimentoNuovoIngressoService.inserimentoNuovoIngresso("VRDLGU80A01L781G", "Luigi Verdi");

        Assert.assertEquals("Ingresso per Luigi Verdi avvenuto correttamente", messaggio);

    }

    @Test
    public void inserimentoNuovoIngresso_RecordGiaPresente_Test() {

        when(ingressoRepository.estrazioneIngressoGiornaliero(anyString())).thenReturn(new DatiIngresso());

        String messaggio = inserimentoNuovoIngressoService.inserimentoNuovoIngresso("VRDLGU80A01L781G", "Luigi Verdi");

        Assert.assertEquals("Ingresso gia avvenuto in data odierna", messaggio);

    }



}
