package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class EstraiClientiServiceTest {


    @Test
    public void estrazioneClientePerCF_Test() {
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);
        EstraiClientiService estraiClientiService = new EstraiClientiService(clienteRepository);

        Mockito.when(clienteRepository.estrazionePerCodiceFiscale(Mockito.anyString())).thenReturn(new Cliente());

        Cliente clienteEstratto = estraiClientiService.estrazioneClientePerCodiceFiscale("AAA");

        Assert.assertNotNull(clienteEstratto);
    }

    @Test
    public void estrazioneClientePerCF_CLienteNonTrovato_Test() {
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);
        EstraiClientiService estraiClientiService = new EstraiClientiService(clienteRepository);

        Mockito.when(clienteRepository.estrazionePerCodiceFiscale(Mockito.anyString())).thenReturn(null);

        Cliente clienteEstratto = estraiClientiService.estrazioneClientePerCodiceFiscale("AAA");

        Assert.assertNull(clienteEstratto);
    }

    @Test(expected = InputErratoException.class)
    public void estrazioneClientePerCF_InputNull_Test() {
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);
        EstraiClientiService estraiClientiService = new EstraiClientiService(clienteRepository);

        Mockito.when(clienteRepository.estrazionePerCodiceFiscale(Mockito.anyString())).thenReturn(new Cliente());

        estraiClientiService.estrazioneClientePerCodiceFiscale(null);
    }

    @Test(expected = InputErratoException.class)
    public void estrazioneClientePerCF_InputVuoto_Test() {
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);
        EstraiClientiService estraiClientiService = new EstraiClientiService(clienteRepository);

        Mockito.when(clienteRepository.estrazionePerCodiceFiscale(Mockito.anyString())).thenReturn(new Cliente());

        estraiClientiService.estrazioneClientePerCodiceFiscale("");
    }
}
