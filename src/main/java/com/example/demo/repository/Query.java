package com.example.demo.repository;

import java.util.Date;

import static com.example.demo.utility.Dates.format;

public class Query {

    /**
     * Inserimento di un nuovo ingresso
     * INSERT INTO Ingressi ('codiceFiscale', 'nominativo', 'dataIngresso');
     */
    public static String inserimentoNuovoIngresso(String codiceFiscale, String nominativo, Date dataIngresso){
        return "INSERT INTO Ingressi VALUES (" +
                "'" + codiceFiscale + "', " +
                "'" + nominativo + "', " +
                "'" + format(dataIngresso) + "'" +
                ");";
    }

    /**
     * Estrazione di un ingresso
     * SELECT * FROM Ingressi
     * WHERE Codice_Fiscale = 'codiceFiscale'
     * AND   Data_Ingresso = CURRENT DATE);
     */
    public static String estrazioneIngressoGiornaliero(String codiceFiscale){
        return "SELECT * FROM Ingressi" +
                " WHERE Codice_Fiscale = '" + codiceFiscale + "'" +
                " AND Data_Ingresso = CURRENT_DATE";
    }
}
