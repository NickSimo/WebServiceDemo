package com.example.demo.exceptions;

public class ValidazioneException extends Exception {
  private String livello;
  private String campoErrore;

  public String getLivello() {
    return livello;
  }

  public void setLivello(String livello) {
    this.livello = livello;
  }

  public String getCampoErrore() {
    return campoErrore;
  }

  public void setCampoErrore(String campoErrore) {
    this.campoErrore = campoErrore;
  }

  public ValidazioneException(String messaggio, String livello, String campoErrore) {
    super(messaggio);
    this.livello = livello;
    this.campoErrore = campoErrore;
  }

  public ValidazioneException(String messaggio, String livello) {
    super(messaggio);
    this.livello = livello;
  }
}
