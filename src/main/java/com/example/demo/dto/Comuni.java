package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comuni {
private String nome;
private String codice;
private String regione;
private String provincia;
private String codiceCatastale;
private String cap;
private Coordinate coordinate;
}
