package com.example.demo.entity.rowmapper;

import com.example.demo.entity.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper<Cliente> {
    @Override
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCognome(rs.getString("cognome"));
        cliente.setCodice_fiscale(rs.getString("codice_fiscale"));
        cliente.setIndirizzo_residenza(rs.getString("indirizzo_residenza"));
        return cliente;
    }
}
