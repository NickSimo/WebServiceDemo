package com.example.demo.controller;

import com.example.demo.FakeDatabaseConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(FakeDatabaseConfiguration.class)
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
    public void estrazioneClientePerCodiceFiscale_Ok_Test() throws Exception {
        mvc.perform(get("/clienti/estrazione-per-cf?cf=RSSMRO12D19L78T"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Mario"))
                .andExpect(jsonPath("cognome").value("Rossi"))
                .andExpect(jsonPath("codice_fiscale").value("RSSMRO12D19L78T"))
                .andExpect(jsonPath("indirizzo_residenza").value("via alberto dominutti 6"));
    }

    @Test
    public void estrazioneClientePerCodiceFiscale_Ko_Test() throws Exception {
        mvc.perform(get("/clienti/estrazione-per-cf?cf="))
                .andExpect(status().isInternalServerError());
    }






}
