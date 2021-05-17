package com.example.demo.repository;

import com.example.demo.EndToEndConfiguration;
import com.example.demo.entity.DatiNuovoIngresso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.demo.JsonComposer.getInputJson;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(EndToEndConfiguration.class)
public class IngressoRepositoryTest {

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
