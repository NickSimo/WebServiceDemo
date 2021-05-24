package com.example.demo.repository;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.Cliente;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class ClienteRepositoryTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void estrazioneClienti_5Clienti_Test() {

        List<Cliente> clienti = clienteRepository.estraiTuttiIClienti();

        Assert.assertEquals(5,clienti.size());
    }

    @Test
    public void estrazioneClientePerCodiceFiscale_RecordTrovato_Test(){

        Cliente clienteEstratto = clienteRepository.estrazioneClientePerCodiceFiscale("RSSMRO12D19L78T");

        Assert.assertEquals("Mario", clienteEstratto.getNome());
        Assert.assertEquals("Rossi", clienteEstratto.getCognome());
        Assert.assertEquals("RSSMRO12D19L78T", clienteEstratto.getCodice_fiscale());
        Assert.assertEquals("via alberto dominutti 6", clienteEstratto.getIndirizzo_residenza());
    }

    @Test
    public void estrazioneClientePerCodiceFiscale_RecordNonTrovato_Test(){

        Cliente clienteEstratto = clienteRepository.estrazioneClientePerCodiceFiscale("BBB");

        Assert.assertNull(clienteEstratto);
    }

}
