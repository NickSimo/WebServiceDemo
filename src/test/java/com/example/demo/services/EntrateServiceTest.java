package com.example.demo.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.demo.repositories.EntrataRepository;
import java.sql.Date;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EntrateServiceTest {
  private EntrataRepository entrataRepository = Mockito.mock(EntrataRepository.class);

  @Test
  public void entra_ilMetodoEntraVieneCorrettamenteChiamato() {
    EntrateService entrateService = new EntrateService(entrataRepository);
    when(entrataRepository.entra(eq("MRSSWERTYGDL"), eq("rossi"), any(Date.class)))
        .thenReturn("success");
    String esito = entrateService.entra("MRSSWERTYGDL", "rossi", Date.valueOf(LocalDate.now()));
    assertThat(esito).isEqualTo("success");
  }

  @Test
  public void entra_ilMetodoEntraNonVieneCorrettamenteChiamato() {
    EntrateService entrateService = new EntrateService(entrataRepository);
    when(entrataRepository.entra(eq("MRSSWERTYGDL"), eq("rossi"), any(Date.class)))
        .thenReturn("success");
    String esito = entrateService.entra("MRSSWERTYGDL", "bianchi", Date.valueOf(LocalDate.now()));
    assertThat(esito).isNotEqualTo("success");
  }

  @Test
  public void esce_ilMetodoEsceVieneCorrettamenteChiamato() {
    EntrateService entrateService = new EntrateService(entrataRepository);
    when(entrataRepository.esce(eq("MRSSWERTYGDL"), eq("rossi"))).thenReturn("success");
    String esito = entrateService.esce("MRSSWERTYGDL", "rossi");
    assertThat(esito).isEqualTo("success");
  }

  @Test
  public void esce_ilMetodoEsceNonVieneCorrettamenteChiamato() {
    EntrateService entrateService = new EntrateService(entrataRepository);
    when(entrataRepository.esce(eq("MRSSWERTYGDL"), eq("rossi"))).thenReturn("success");
    String esito = entrateService.esce("MRSSWERTYGDL", "bianchi");
    assertThat(esito).isNotEqualTo("success");
  }


}
