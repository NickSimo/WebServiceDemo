package com.example.demo.adapter;

import com.example.demo.dto.Comuni;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ComuniRestAdapter {

  private final RestTemplate restTemplate;

  public List<Comuni> chiama() {
    String url = "https://comuni-ita.herokuapp.com/api/comuni";
    ResponseEntity<Comuni[]> response = restTemplate.getForEntity(url, Comuni[].class);
    Comuni[] comunis = response.getBody();
    return Arrays.asList(comunis);
  }
}
