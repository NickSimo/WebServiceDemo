package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatiAnagrafici {
    private String nome;
    private String cognome;
    private String codice_fiscale;
    private Date data_nascita;
}
