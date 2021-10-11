package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Recapiti {

  private String indirizzo_residenza;
  private String comune_residenza;
  private boolean fl_residenza_estero;
  private String indirizzo_domicilio;
  private String comune_domicilio;
  private boolean fl_domicilio_estero;
  private String nazione;
}
