package com.example.demo.services;

import com.example.demo.dto.Cliente;
import com.example.demo.dto.Recapiti;
import com.example.demo.exceptions.ValidazioneException;
import com.example.demo.repositories.ClientiRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ClientiService {
  private final ClientiRepository clientiRepository;

  public Cliente estraiCliente(String codiceFiscale) {
    return clientiRepository.estraiCliente(codiceFiscale);
  }

  public List<Cliente> estraiTuttiClienti() {
    return clientiRepository.estraiClienti();
  }

  public String salvaCliente(Cliente cliente) throws ValidazioneException {
    StringBuilder messaggiAccodati = new StringBuilder("");
    int eta =
        Date.valueOf(LocalDate.now()).getYear()
            - cliente.getDatiAnagrafici().getData_nascita().getYear();

    if (cliente.getDatiAnagrafici().getCodice_fiscale().length() != 16) {
      throw new ValidazioneException(
          "codice fiscale di lunghezza inferiore di quello standard :",
          "ERROR",
          cliente.getDatiAnagrafici().getCodice_fiscale());
    }

    if (codiceFiscaleSoloNumerico(cliente)) {
      messaggiAccodati
          .append(" codice fiscale solo numerico :")
          .append(cliente.getDatiAnagrafici().getCodice_fiscale());
    }

    if (codiceFiscaleSoloAlfabetico(cliente)) {
      messaggiAccodati
          .append(" codice fiscale solo di lettere :")
          .append(cliente.getDatiAnagrafici().getCodice_fiscale());
    }

    if (cliente.getDatiAnagrafici().getCognome().isEmpty()) {
      throw new ValidazioneException(
          " cognome assente", "ERROR", cliente.getDatiAnagrafici().getCognome());
    }

    if (isStringUpperCase(cliente.getDatiAnagrafici().getCognome())) {
      messaggiAccodati
          .append(" cognome in maiuscolo:")
          .append(cliente.getDatiAnagrafici().getCognome());
    }

    if (isStringUpperCase(cliente.getDatiAnagrafici().getNome())) {
      messaggiAccodati.append(" nome in maiuscolo:").append(cliente.getDatiAnagrafici().getNome());
    }

    if (StringUtils.isEmpty(cliente.getDatiAnagrafici().getCognome())) {
      throw new ValidazioneException(
          "cognome vuoto :", "ERROR", cliente.getDatiAnagrafici().getCognome());
    }
    if (eta < 18) {
      throw new ValidazioneException(
          "utente minorenne :", "ERROR", cliente.getDatiAnagrafici().getData_nascita().toString());
    }
    if (!messaggiAccodati.toString().equals("")) {
      throw new ValidazioneException(messaggiAccodati.toString(), "WARNING");
    }
    return clientiRepository.salvaCliente(cliente);
  }

  public Cliente estraiPerNomeECognome(String nome, String cognome) {
    return clientiRepository.estraiPerNomeECognome(nome, cognome);
  }

  private boolean codiceFiscaleSoloNumerico(Cliente cliente) {
    return cliente.getDatiAnagrafici().getCodice_fiscale().matches("[0-9]+")
        && cliente.getDatiAnagrafici().getCodice_fiscale().length() > 2;
  }

  private boolean codiceFiscaleSoloAlfabetico(Cliente cliente) {
    return cliente.getDatiAnagrafici().getCodice_fiscale().matches("[a-zA-Z]+");
  }

  private static boolean isStringUpperCase(String str) {

    char[] charArray = str.toCharArray();

    for (int i = 0; i < charArray.length; i++) {

      if (Character.isLetter(charArray[i])) {

        if (!Character.isUpperCase(charArray[i])) return false;
      }
    }

    return true;
  }

  public String aggiornaRecapito(Recapiti recapito, String codice_fiscale) {
    return clientiRepository.aggiornaRecapito(recapito, codice_fiscale);
  }

  public String estraiAnagrafica(String codiceFiscale) {
    Cliente cliente = clientiRepository.estraiCliente(codiceFiscale);
    if (Objects.nonNull(cliente)) {
      return "il cliente si chiama "
          .concat(cliente.getDatiAnagrafici().getNome())
          .concat(" ")
          .concat(cliente.getDatiAnagrafici().getCognome());
    }
    return "errore";
  }

  public String isMaggiorenne(String codiceFiscale) {
    Cliente cliente = clientiRepository.estraiCliente(codiceFiscale);
    int eta =
        Date.valueOf(LocalDate.now()).getYear()
            - cliente.getDatiAnagrafici().getData_nascita().getYear();
    if (eta < 18) {
      return "e minorenne";
    } else {
      return "e maggiorenne";
    }
  }
}
