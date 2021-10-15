package com.example.demo.services;

import com.example.demo.adapter.ComuniRestAdapter;
import com.example.demo.dto.Comuni;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComuniAdapterService {
  private final ComuniRestAdapter comuniRestAdapter;

  public List<Comuni> chiama() throws Exception {
    List<Comuni> listaRitornata = comuniRestAdapter.chiama();
    if( Objects.nonNull(listaRitornata) && listaRitornata.size() > 0){
      return listaRitornata;
    }else {
      throw new Exception("la lista ricevuta è vuota");
    }
  }
}
