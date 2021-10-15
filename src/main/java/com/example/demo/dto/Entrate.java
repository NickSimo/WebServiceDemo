package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Entrate {
  private String cognome;
  private String codiceFiscale;
  private Date dataIngresso;
}
