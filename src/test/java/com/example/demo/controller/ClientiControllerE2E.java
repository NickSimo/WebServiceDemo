package com.example.demo.controller;

import ch.qos.logback.core.net.server.Client;
import com.example.demo.EndToEndConfiguration;
import com.example.demo.JsonComposer;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.DatiIngresso;
import com.example.demo.entity.DatiNuovoIngresso;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.IngressoRepository;
import com.example.demo.repository.Query;
import com.example.demo.rowmapper.ClienteRowMapper;
import com.example.demo.rowmapper.DatiIngressoRowMapper;
import com.example.demo.service.EstraiClientiService;
import com.example.demo.service.InserimentoNuovoIngressoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static com.example.demo.JsonComposer.getInputJson;
import static org.junit.Assert.assertEquals;
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
    public void estraiClienti_Test() throws Exception {
        mvc.perform(get("/clienti/elenco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].datiAnagrafici.nome").value("Mario"))
                .andExpect(jsonPath("[0].datiAnagrafici.cognome").value("Rossi"));
    }

    @Test
    public void inserimentoNuovoIngresso_Test() throws Exception {
        DatiNuovoIngresso datiNuovoIngresso = new DatiNuovoIngresso("RSSMRO12D19L78T", "Mario Rossi");

        MvcResult result = mvc.perform(post("/clienti/ingressi/nuovo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getInputJson(datiNuovoIngresso)))
                .andExpect(status().isOk())
                .andReturn();

        String message = result.getResponse().getContentAsString();
        assertEquals("Ingresso per Mario Rossi avvenuto correttamente", message);

        int numeroDiRecordInseriti = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Ingressi WHERE Codice_Fiscale = 'RSSMRO12D19L78T'", Integer.class);
        assertEquals(1, numeroDiRecordInseriti);

    }

    @Test
    public void inserimentoNuovoIngresso_IngressoGiaAvvenuto_Test() throws Exception {
        DatiNuovoIngresso datiNuovoIngresso = new DatiNuovoIngresso("VRDLGU80A01L781G", "Luigi Verdi");

        MvcResult result = mvc.perform(post("/clienti/ingressi/nuovo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getInputJson(datiNuovoIngresso)))
                .andExpect(status().isOk())
                .andReturn();

        String message = result.getResponse().getContentAsString();
        assertEquals("Ingresso gia avvenuto in data odierna", message);
    }




}
