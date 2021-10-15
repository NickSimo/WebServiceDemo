package com.example.demo.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



import com.example.demo.adapter.ComuniRestAdapter;
import com.example.demo.dto.Comuni;
import com.example.demo.exceptions.ValidazioneException;
import com.example.demo.repositories.EntrataRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ComuniAdapterServiceTest {

  @Mock
  ComuniRestAdapter comuniRestAdapter = Mockito.mock(ComuniRestAdapter .class);;

  @Test
  public void chiamataAlWs_restituisceListaComuniPiena() throws Exception {
    ComuniAdapterService comuniAdapterService = new ComuniAdapterService(comuniRestAdapter);
    List<Comuni> comuniWS = new ArrayList<>();
    Comuni comune = new Comuni();
    comune.setCap("36019");
    comuniWS.add(comune);
    when(comuniRestAdapter.chiama()).thenReturn(comuniWS);
    List<Comuni> comuniList = comuniAdapterService.chiama();
    assertThat(comuniList.size()).isEqualTo(1);
  }

  @Test
  public void chiamataAlWs_restituisceListaComuniVuota_vieneLanciataUnEccezione() throws Exception {
    ComuniAdapterService comuniAdapterService = new ComuniAdapterService(comuniRestAdapter);
    when(comuniRestAdapter.chiama()).thenReturn(new ArrayList<>());
    assertThrows(Exception.class, comuniAdapterService::chiama);
  }

}
