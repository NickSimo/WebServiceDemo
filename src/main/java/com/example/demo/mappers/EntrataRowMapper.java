package com.example.demo.mappers;

import com.example.demo.dto.Entrate;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class EntrataRowMapper implements RowMapper<Entrate> {

  @Override
  public Entrate mapRow(ResultSet resultSet, int i) throws SQLException {
    Entrate entrata = new Entrate();
    entrata.setCodiceFiscale(resultSet.getString("Codice_Fiscale"));
    entrata.setCognome(resultSet.getString("Nominativo"));
    entrata.setDataIngresso(resultSet.getDate("Data_Ingresso"));
    return entrata;
  }
}
