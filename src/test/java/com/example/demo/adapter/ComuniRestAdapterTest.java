package com.example.demo.adapter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



import com.example.demo.FakeDatabaseConfiguration;
import com.example.demo.dto.Comuni;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
@Import(FakeDatabaseConfiguration.class)
class ComuniRestAdapterTest {

  @Autowired RestTemplate restTemplateMoock;

  @Test
  public void effettuaChiamata() {
    ComuniRestAdapter comuniRestAdapter = new ComuniRestAdapter(restTemplateMoock);
    List<Comuni> comuni = comuniRestAdapter.chiama();
    assertThat(comuni.size()).isEqualTo(1);
  }
}
