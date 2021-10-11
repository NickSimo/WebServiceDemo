package com.example.demo.repositories;

import com.example.demo.dto.Cliente;

public class Query {

  public static String estrazioneCliente(String codiceFiscale) {
    return "SELECT * FROM clienti" + " WHERE codice_fiscale = '" + codiceFiscale + "'";
  }

  public static String estrazioniClienti() {
    return "SELECT * FROM clienti";
  }

  public static String inserimentoNuovoCliente(Cliente cliente) {
    return "insert into clienti (indirizzo_residenza,comune_residenza,indirizzo_domicilio,comune_domicilio,nazione,nome,cognome,codice_fiscale,numero_carta_identita)"
        + "VALUES("
        + "'"
        + cliente.getRecapiti().getIndirizzo_residenza()
        + "'"
        + ","
        + "'"
        + cliente.getRecapiti().getComune_residenza()
        + "'"
        //        + ","
        //        + cliente.getRecapiti().isFl_residenza_estero()
        + ","
        + "'"
        + cliente.getRecapiti().getIndirizzo_domicilio()
        + "'"
        + ","
        + "'"
        + cliente.getRecapiti().getComune_domicilio()
        + "'"
        + ","
        //        + cliente.getRecapiti().isFl_domicilio_estero()
        //        + ","
        + "'"
        + cliente.getRecapiti().getNazione()
        + "'"
        + ","
        + "'"
        + cliente.getDatiAnagrafici().getNome()
        + "'"
        + ","
        + "'"
        + cliente.getDatiAnagrafici().getCognome()
        + "'"
        + ","
        + "'"
        + cliente.getDatiAnagrafici().getCodice_fiscale()
        + "'"
        //        + ","
        //        + cliente.getDatiAnagrafici().getData_nascita()
        + ","
        + "'"
        + cliente.getNormativa().getNumero_carta_identita()
        + "'"
        + ")";
  }

  public static String inserimentoPerNomeECognome(String nome, String cognome) {
    return "select * from clienti where nome = " + "'" + nome + "'" + " and cognome = " + "'" + cognome + "'";
  }
}
