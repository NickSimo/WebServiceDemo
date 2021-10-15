package com.example.demo.adapter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;



import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.dto.Comuni;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Import(FakeDatabaseConfiguration.class)
@SpringBootTest
class ComuniRestAdapterTest {

  @Mock
  RestTemplate restTemplate;

  @Test
  public void effettuaChiamata() {
    Comuni[] comunis = new Comuni[1];
    Comuni comune = new Comuni();
    comune.setCap("36019");
    comunis[0] = comune;
    ResponseEntity<Comuni[]> response = new ResponseEntity<>(comunis, HttpStatus.ACCEPTED);

    when(restTemplate.getForEntity("https://comuni-ita.herokuapp.com/api/comuni", Comuni[].class)).thenReturn(response);
    ComuniRestAdapter comuniRestAdapter = new ComuniRestAdapter(restTemplate);
    List<Comuni> comuni = comuniRestAdapter.chiama();
    assertThat(comuni.size()).isPositive();
  }
}
