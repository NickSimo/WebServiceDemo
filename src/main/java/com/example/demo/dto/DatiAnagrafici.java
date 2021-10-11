package com.example.demo.dto;

import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatiAnagrafici {
  private String nome;
  private String cognome;
  private String codice_fiscale;
  private Date data_nascita;
}
