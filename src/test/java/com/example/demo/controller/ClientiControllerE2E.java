package com.example.demo.controller;

import ch.qos.logback.core.net.server.Client;
import com.example.demo.EndToEndConfiguration;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.DatiIngresso;
import com.example.demo.entity.DatiNuovoIngresso;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.IngressoRepository;
import com.example.demo.rowmapper.ClienteRowMapper;
import com.example.demo.service.EstraiClientiService;
import com.example.demo.service.InserimentoNuovoIngressoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

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
        mvc.perform(get("/clienti/elenco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].datiAnagrafici.nome").value("Mario"))
                .andExpect(jsonPath("[0].datiAnagrafici.cognome").value("Rossi"));
    }

    @Test
    public void inserimentoNuovoIngressoTest() throws Exception {
        DatiNuovoIngresso datiNuovoIngresso = new DatiNuovoIngresso("RSSMRO12D19L78T", "Mario Rossi");

        mvc.perform(post("/clienti/ingressi/nuovo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getInputJson(datiNuovoIngresso)))
                .andExpect(status().isOk());
    }

    public static String getInputJson(DatiNuovoIngresso datiNuovoIngresso) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonRequest = gson.toJson(datiNuovoIngresso);
        return jsonRequest;
    }



}
