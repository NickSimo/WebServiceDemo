package com.example.demo.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.dto.Cliente;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class ClientiRepositoryTest {

  private ClientiRepository clientiRepository;

  @Autowired JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void before() {
    clientiRepository = new ClientiRepository(jdbcTemplate);
  }

  @Test
  public void cliente_vieneEstrattoUnCliente_conIParametriDiInput() {

    Cliente cliente = clientiRepository.estraiCliente("RSSMRO12D19L78T");
    assertThat(cliente.getDatiAnagrafici().getNome()).isEqualTo("Mario");
  }

  @Test
  public void cliente_nonVieneEstrattoUnCliente() {
    Cliente cliente = clientiRepository.estraiCliente("RSSMRO12D19L78K");
    assertThat(cliente).isNull();
  }

  @Test
  public void cliente_estrattiTuttiIClienti() {
    List<Cliente> clienti = clientiRepository.estraiClienti();
    assertThat(clienti.size()).isGreaterThan(0);
  }
}
