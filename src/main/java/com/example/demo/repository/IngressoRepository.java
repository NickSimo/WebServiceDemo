package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.DatiIngresso;
import com.example.demo.rowmapper.ClienteRowMapper;
import com.example.demo.rowmapper.DatiIngressoRowMapper;
import com.example.demo.service.utility.Dates;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.example.demo.service.utility.Dates.format;

@Repository
@RequiredArgsConstructor
public class IngressoRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Inserimento di un nuovo ingresso
     * INSERT INTO Ingressi ('codiceFiscale', 'nominativo', 'dataIngresso');
     */
    public void inserimentoNuovoIngresso(String codiceFiscale, String nominativo, Date dataIngresso) {
        jdbcTemplate.update("INSERT INTO Ingressi VALUES (" +
                "'" + codiceFiscale + "', " +
                "'" + nominativo + "', " +
                "'" + format(dataIngresso) + "'" +
                ");");
    }

    /**
     * Estrazione di un ingresso
     * SELECT * FROM Ingressi
     * WHERE Codice_Fiscale = 'codiceFiscale'
     * AND   Data_Ingresso = CURRENT DATE);
     */
    public DatiIngresso estrazioneIngressoGiornaliero(String codiceFiscale) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Ingressi" +
                            " WHERE Codice_Fiscale = '" + codiceFiscale + "'" +
                            " AND Data_Ingresso = CURRENT_DATE",
                    new DatiIngressoRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
