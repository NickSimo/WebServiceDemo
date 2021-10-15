package com.example.demo.services;

import com.example.demo.dto.Cliente;
import com.example.demo.repositories.EntrataRepository;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntrateService {
  private final EntrataRepository entrataRepository;

  public String entra(String codiceFiscale, String cognome, Date dateEntrata) {
    return entrataRepository.entra(codiceFiscale, cognome, dateEntrata);
  }

  public String esce(String codiceFiscale, String cognome) {
    return entrataRepository.esce(codiceFiscale, cognome);
  }
}
