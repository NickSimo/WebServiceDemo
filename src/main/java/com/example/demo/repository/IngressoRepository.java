package com.example.demo.repository;

import com.example.demo.entity.DatiIngresso;
import com.example.demo.rowmapper.DatiIngressoRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@RequiredArgsConstructor
public class IngressoRepository {

    private final JdbcTemplate jdbcTemplate;


    public void inserimentoNuovoIngresso(String codiceFiscale, String nominativo, Date dataIngresso) {
        jdbcTemplate.update(Query.inserimentoNuovoIngresso(codiceFiscale, nominativo, dataIngresso));
    }


    public DatiIngresso estrazioneIngressoGiornaliero(String codiceFiscale) {
        try {
            return jdbcTemplate.queryForObject(Query.estrazioneIngressoGiornaliero(codiceFiscale), new DatiIngressoRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
