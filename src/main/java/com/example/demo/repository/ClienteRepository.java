package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.rowmapper.ClienteRowMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;




    public List<Cliente> estraiTuttiIClienti() {
        try {
            return jdbcTemplate.query("SELECT * FROM clienti", new ClienteRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Cliente estrazionePerCodiceFiscale(String cf) {
        try{
            return jdbcTemplate.queryForObject(
                "SELECT * FROM clienti WHERE codice_fiscale = '" +cf+"'",
                new ClienteRowMapper());}
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
