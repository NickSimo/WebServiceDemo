package com.example.demo.repositories;

import com.example.demo.dto.Cliente;
import com.example.demo.dto.Recapiti;
import java.time.LocalDate;
import java.util.Date;

public class Query {

  public static String estrazioneCliente(String codiceFiscale) {
    return "SELECT * FROM clienti" + " WHERE codice_fiscale = '" + codiceFiscale + "'";
  }

  public static String estrazioniClienti() {
    return "SELECT * FROM clienti";
  }

  public static String inserimentoNuovoCliente(Cliente cliente) {
    return "insert into clienti (indirizzo_residenza,comune_residenza,indirizzo_domicilio,comune_domicilio,nazione,nome,cognome,codice_fiscale,data_nascita,numero_carta_identita)"
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
        + ","
        + "'"
        + cliente.getDatiAnagrafici().getData_nascita().toString()
        + "'"
        + ","
        + "'"
        + cliente.getNormativa().getNumero_carta_identita()
        + "'"
        + ")";
  }

  public static String inserimentoPerNomeECognome(String nome, String cognome) {
    return "select * from clienti where nome = "
        + "'"
        + nome
        + "'"
        + " and cognome = "
        + "'"
        + cognome
        + "'";
  }

  public static String inserimentoEntrata(String codiceFiscale, String cognome, Date dataIngresso) {
    return "insert into ingressi "
        .concat("(")
        .concat("Codice_Fiscale,Nominativo,Data_Ingresso")
        .concat(")")
        .concat("values ")
        .concat("(")
        .concat("'")
        .concat(codiceFiscale)
        .concat("'")
        .concat(",")
        .concat("'")
        .concat(cognome)
        .concat("'")
        .concat(",")
        .concat("'")
        .concat(dataIngresso.toString())
        .concat("'")
        .concat(")");
  }

  public static String ricercaEntrata(String codiceFiscale, String cognome) {
    return "select count(*) from ingressi where Codice_Fiscale = "
        .concat("'")
        .concat(codiceFiscale)
        .concat("'")
        .concat(" and ")
        .concat("Nominativo = ")
        .concat("'")
        .concat(cognome)
        .concat("'");
  }

  public static String eliminaEntrata(String codiceFiscale, String cognome) {
    return "delete from ingressi where Codice_Fiscale = "
        .concat("")
        .concat("'")
        .concat(codiceFiscale)
        .concat("'")
        .concat(" and ")
        .concat("Nominativo = ")
        .concat("'")
        .concat(cognome)
        .concat("'");
  }

  public static String aggiornaRecapito(Recapiti recapito, String codiceFiscale) {
    return "update clienti set indirizzo_residenza = "
        .concat("'")
        .concat(recapito.getIndirizzo_residenza())
        .concat("'")
        .concat(",")
        .concat("comune_residenza = ")
        .concat("'")
        .concat(recapito.getComune_residenza())
        .concat("'")
        .concat("where codice_fiscale = ")
        .concat("'")
        .concat(codiceFiscale)
        .concat("'");
  }
}
