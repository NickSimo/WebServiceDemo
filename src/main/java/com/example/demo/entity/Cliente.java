package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Cliente {
    private Long id;
    private String nome;
    private String cognome;
    private String codice_fiscale;
    private String indirizzo_residenza;
}
