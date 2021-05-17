package com.example.demo.rowmapper;

import com.example.demo.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatiIngressoRowMapper implements RowMapper<DatiIngresso> {
    @Override
    public DatiIngresso mapRow(ResultSet rs, int rowNum) throws SQLException {
        DatiIngresso datiIngresso = new DatiIngresso();
        datiIngresso.setCodiceFiscale(rs.getString("Codice_Fiscale"));
        datiIngresso.setNominativo(rs.getString("Nominativo"));
        datiIngresso.setDataIngresso(rs.getDate("Data_Ingresso"));
        return datiIngresso;
    }
}
