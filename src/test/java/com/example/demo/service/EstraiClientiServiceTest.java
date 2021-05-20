package com.example.demo.service;

import com.example.demo.entity.Cliente;
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
    public void estrazioneClienti_2ClientiPresenti_Test(){
        ArrayList<Cliente> listaCon2Clienti = new ArrayList<>();
        listaCon2Clienti.add(new Cliente());
        listaCon2Clienti.add(new Cliente());
        Mockito.when(clienteRepository.estraiTuttiIClienti()).thenReturn(listaCon2Clienti);

        List<Cliente> clienti = estraiClientiService.estraiTuttiIClienti();

        Assert.assertEquals(2, clienti.size());
    }

}
