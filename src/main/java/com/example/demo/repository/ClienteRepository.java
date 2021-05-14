package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import com.example.demo.rowmapper.ClienteRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClienteRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Cliente> estraiTuttiIClienti() {
        try {
            return jdbcTemplate.query("SELECT * FROM clienti", new ClienteRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
