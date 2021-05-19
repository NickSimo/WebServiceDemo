package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class EstraiClientiServiceTest {

    ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);
    EstraiClientiService estraiClientiService = new EstraiClientiService(clienteRepository);

    @Test
    public void estrazioneClientePerCodiceFiscale_OK_Test(){

        Mockito.when(clienteRepository.estrazioneClientePerCodiceFiscale(Mockito.anyString())).thenReturn(new Cliente());

        Cliente cliente = estraiClientiService.estrazionePerCodiceFiscale("AAA");

        Assert.assertNotNull(cliente);
    }

    @Test
    public void estrazioneClientePerCodiceFiscale_NessunRecordTrovato_Test(){

        Mockito.when(clienteRepository.estrazioneClientePerCodiceFiscale(Mockito.anyString())).thenReturn(null);

        Cliente cliente = estraiClientiService.estrazionePerCodiceFiscale("AAA");

        Assert.assertNull(cliente);
    }

    @Test(expected = InputErratoException.class)
    public void estrazioneClientePerCodiceFiscale_CodiceFiscaleNull_Test(){

        estraiClientiService.estrazionePerCodiceFiscale(null);

    }

    @Test(expected = InputErratoException.class)
    public void estrazioneClientePerCodiceFiscale_CodiceFiscaleVuoto_Test(){

        estraiClientiService.estrazionePerCodiceFiscale("");

    }
}
