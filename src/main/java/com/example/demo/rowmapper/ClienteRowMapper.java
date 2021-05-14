package com.example.demo.rowmapper;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.DatiAnagrafici;
import com.example.demo.entity.Normativa;
import com.example.demo.entity.Recapiti;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper<Cliente> {
    @Override
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setDatiAnagrafici(datiAnagraficiMapper(rs));
        cliente.setNormativa(normativaMapper(rs));
        cliente.setRecapiti(recapitiMapper(rs));
        return cliente;
    }
    private DatiAnagrafici datiAnagraficiMapper(ResultSet rs) throws SQLException {
        DatiAnagrafici datiAnagrafici = new DatiAnagrafici();
        datiAnagrafici.setNome(rs.getString("nome"));
        datiAnagrafici.setCognome(rs.getString("cognome"));
        datiAnagrafici.setCodice_fiscale(rs.getString("codice_fiscale"));
        datiAnagrafici.setData_nascita(rs.getDate("data_nascita"));
        return datiAnagrafici;
    }

    private Normativa normativaMapper(ResultSet rs) throws SQLException {
        Normativa normativa = new Normativa();
        normativa.setNumero_carta_identita(rs.getString("numero_carta_identita"));
        return normativa;
    }

    private Recapiti recapitiMapper(ResultSet rs) throws SQLException {
        Recapiti recapiti = new Recapiti();
        recapiti.setComune_domicilio(rs.getString("comune_domicilio"));
        recapiti.setComune_residenza(rs.getString("comune_residenza"));
        recapiti.setFl_domicilio_estero(rs.getBoolean("fl_domicilio_estero"));
        recapiti.setFl_residenza_estero(rs.getBoolean("fl_residenza_estero"));
        recapiti.setIndirizzo_domicilio(rs.getString("indirizzo_domicilio"));
        recapiti.setIndirizzo_residenza(rs.getString("indirizzo_residenza"));
        recapiti.setNazione(rs.getString("nazione"));
        return recapiti;
    }
}
