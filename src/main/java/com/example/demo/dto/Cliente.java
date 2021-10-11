package com.example.demo.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cliente {
private Long id;
private Recapiti recapiti = new Recapiti();
private DatiAnagrafici datiAnagrafici = new DatiAnagrafici();
private Normativa normativa = new Normativa();
}
