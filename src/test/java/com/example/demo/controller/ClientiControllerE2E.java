package com.example.demo.controller;

import com.example.demo.EndToEndConfiguration;
import com.example.demo.entity.Cliente;
import com.example.demo.rowmapper.ClienteRowMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(EndToEndConfiguration.class)
public class ClientiControllerE2E {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Test
    public void estraiClientiTest() throws Exception {
        mvc.perform(get("/clienti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].datiAnagrafici.nome").value("Mario"))
                .andExpect(jsonPath("[0].datiAnagrafici.cognome").value("Rossi"));
    }





}
