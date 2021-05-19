package com.example.demo.repository;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.Cliente;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class ClienteRepositoryTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void estrazioneClientePerCodiceFiscale_OK_Test() {

        jdbcTemplate.update("INSERT INTO clienti VALUES (1, 'Mario', 'Rossi', 'AAA', 'Via dei mille 10')");

        Cliente cliente = clienteRepository.estrazioneClientePerCodiceFiscale("AAA");

        Assert.assertEquals("AAA", cliente.getCodice_fiscale());
        Assert.assertEquals("Mario", cliente.getNome());
        Assert.assertEquals("Rossi", cliente.getCognome());
        Assert.assertEquals("Via dei mille 10", cliente.getIndirizzo_residenza());
    }

    @Test
    public void estrazioneClientePerCodiceFiscale_RecordNonTrovato_Test() {

        jdbcTemplate.update("INSERT INTO clienti VALUES (1, 'Mario', 'Rossi', 'AAA', 'Via dei mille 10')");

        Cliente cliente = clienteRepository.estrazioneClientePerCodiceFiscale("BBB");

        Assert.assertNull(cliente);
    }
}
