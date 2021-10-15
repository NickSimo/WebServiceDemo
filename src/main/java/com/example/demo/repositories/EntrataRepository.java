package com.example.demo.repositories;

import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EntrataRepository {

  private final JdbcTemplate jdbcTemplate;

  public String entra(String codiceFiscale, String cognome, Date now) {
    try {
      int utentiEntrati =
          jdbcTemplate.queryForObject(Query.ricercaEntrata(codiceFiscale, cognome), Integer.class);
      if (utentiEntrati == 0) {
        jdbcTemplate.execute(Query.inserimentoEntrata(codiceFiscale, cognome, now));
      } else {
        return "problema nella registrazione dell'ingresso";
      }
      return "ingresso registrato";
    } catch (Exception e) {
      return "problema nella registrazione dell'ingresso";
    }
  }

  public String esce(String codice_fiscale, String cognome) {
    try {
      int utentiPresenti =
          jdbcTemplate.queryForObject(Query.ricercaEntrata(codice_fiscale, cognome), Integer.class);
      if (utentiPresenti != 0) {
        jdbcTemplate.execute(Query.eliminaEntrata(codice_fiscale, cognome));
      }else {
        return "nessun utente entrato deve uscire";
      }
    } catch (Exception e) {
      return "problema nella eliminazione dell'ingresso";
    }
    return "ingresso eliminato";
  }
}
