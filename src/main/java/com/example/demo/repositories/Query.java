package com.example.demo.repositories;

public class Query {

  public static String estrazioneCliente(String codiceFiscale) {
    return "SELECT * FROM clienti" + " WHERE codice_fiscale = '" + codiceFiscale + "'";
  }

  public static String estrazioniClienti() {
    return "SELECT * FROM clienti";
  }
}
