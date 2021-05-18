package com.example.demo.repository;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class ClienteRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void estrazionePerCodiceFiscale_Test(){

        //inserire il record che vogliamo venga estratto

        Cliente recordEstratto = clienteRepository.estrazionePerCodiceFiscale("RSSMRO12D19L78T");

        Assert.assertEquals("Mario", recordEstratto.getDatiAnagrafici().getNome());
        Assert.assertEquals("Rossi", recordEstratto.getDatiAnagrafici().getCognome());
        Assert.assertEquals("RSSMRO12D19L78T", recordEstratto.getDatiAnagrafici().getCodice_fiscale());



    }
}
