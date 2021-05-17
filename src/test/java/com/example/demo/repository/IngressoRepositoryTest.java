package com.example.demo.repository;

import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.entity.DatiIngresso;
import com.example.demo.entity.DatiNuovoIngresso;
import com.example.demo.rowmapper.DatiIngressoRowMapper;
import com.example.demo.utility.Dates;
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
import static com.example.demo.utility.Dates.parse;
import static com.example.demo.utility.Dates.today;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
public class IngressoRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IngressoRepository ingressoRepository;

    @Test
    public void inserimentoNuovoIngresso_Test() throws Exception {

        ingressoRepository.inserimentoNuovoIngresso("VRDLGU80A01L781G", "Luigi Verdi", parse("2021-01-01"));

        DatiIngresso datiIngressoInseriti = jdbcTemplate.queryForObject("SELECT * FROM Ingressi", new DatiIngressoRowMapper());

        assertEquals("VRDLGU80A01L781G", datiIngressoInseriti.getCodiceFiscale());
        assertEquals("Luigi Verdi", datiIngressoInseriti.getNominativo().trim());
        assertEquals(parse("2021-01-01"), datiIngressoInseriti.getDataIngresso());

    }

    @Test
    public void estrazioneIngressoGiornaliero_EstrazioneCorretta() throws Exception {

        jdbcTemplate.update("INSERT INTO Ingressi\n" +
                "VALUES\n" +
                "(\n" +
                "    'GLLSDR80A01L781Y',\n" +
                "    'Sandro Gialli',\n" +
                "    CURRENT_DATE \n" +
                ")");

        ingressoRepository.estrazioneIngressoGiornaliero("GLLSDR80A01L781Y");

        DatiIngresso datiIngressoEstratti = jdbcTemplate.queryForObject("SELECT * FROM Ingressi WHERE Codice_Fiscale = 'GLLSDR80A01L781Y'", new DatiIngressoRowMapper());

        assertEquals("GLLSDR80A01L781Y", datiIngressoEstratti.getCodiceFiscale());
        assertEquals("Sandro Gialli", datiIngressoEstratti.getNominativo().trim());
        assertEquals(today(), datiIngressoEstratti.getDataIngresso());

    }

    @Test
    public void estrazioneIngressoGiornaliero_NessunRecordPresente() throws Exception {

        ingressoRepository.estrazioneIngressoGiornaliero("GLLSDR80A01L781Y");

        int datiIngressoEstratti = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Ingressi WHERE Codice_Fiscale = 'GLLSDR80A01L781Y'", Integer.class);

        assertEquals(0, datiIngressoEstratti);

    }


}
