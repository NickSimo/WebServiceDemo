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

}
