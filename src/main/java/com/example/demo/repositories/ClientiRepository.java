package com.example.demo.repositories;

import com.example.demo.dto.Cliente;
import com.example.demo.mappers.ClientiRowMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientiRepository {

  private final JdbcTemplate jdbcTemplate;

  public Cliente estraiCliente(String codiceFiscale) {
    try {
      return jdbcTemplate.queryForObject(
          Query.estrazioneCliente(codiceFiscale), new ClientiRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<Cliente> estraiClienti() {
    try {
      return jdbcTemplate.query(Query.estrazioniClienti(), new ClientiRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public String salvaCliente(Cliente cliente) {
    try {
      jdbcTemplate.execute(Query.inserimentoNuovoCliente(cliente));
      return "Il Cliente è stato inserito correttamente";
    } catch (Exception e) {
      return "errore";
    }
  }

  public Cliente estraiPerNomeECognome(String nome, String cognome) {
    try {
      return jdbcTemplate.queryForObject(
          Query.inserimentoPerNomeECognome(nome, cognome), new ClientiRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}
