package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class EstraiClientiServiceTest {

    ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);
    EstraiClientiService estraiClientiService = new EstraiClientiService(clienteRepository);

    @Test
    public void estrazioneClienti_2ClientiPresenti_Test() {
        ArrayList<Cliente> listaCon2Clienti = new ArrayList<>();
        listaCon2Clienti.add(new Cliente());
        listaCon2Clienti.add(new Cliente());
        Mockito.when(clienteRepository.estraiTuttiIClienti()).thenReturn(listaCon2Clienti);

        List<Cliente> clienti = estraiClientiService.estraiTuttiIClienti();

        Assert.assertEquals(2, clienti.size());
    }

    @Test
    public void estrazioneClientePerCodiceFiscale_EstrazioneAvvenuta_Test() {

        Mockito.when(clienteRepository.estrazioneClientePerCodiceFiscale(Mockito.anyString())).thenReturn(new Cliente());

        Cliente clienteRitornato = estraiClientiService.estrazioneClientePerCodiceFiscale("AAA");

        Assert.assertNotNull(clienteRitornato);
    }

    @Test
    public void estrazioneClientePerCodiceFiscale_NessunRecordTrovato_Test() {

        Mockito.when(clienteRepository.estrazioneClientePerCodiceFiscale(Mockito.anyString())).thenReturn(null);

        Cliente clienteRitornato = estraiClientiService.estrazioneClientePerCodiceFiscale("AAA");

        Assert.assertNull(clienteRitornato);
    }

    @Test(expected = InputErratoException.class)
    public void estrazioneClientePerCodiceFiscale_CodiceFiscaleInputNull_Test() {
        estraiClientiService.estrazioneClientePerCodiceFiscale(null);
    }

    @Test(expected = InputErratoException.class)
    public void estrazioneClientePerCodiceFiscale_CodiceFiscaleInputVuoto_Test() {
        estraiClientiService.estrazioneClientePerCodiceFiscale("");
    }

}
