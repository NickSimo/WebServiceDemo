package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.exception.InputErratoException;
import com.example.demo.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstraiClientiService {

    private final ClienteRepository clienteRepository;


    public List<Cliente> estraiTuttiIClienti() {
        return clienteRepository.estraiTuttiIClienti();
    }

    public Cliente estrazioneClientePerCodiceFiscale(String cf) {
        if (cf == null || cf == "") {
            throw new InputErratoException();
        } else {
            Cliente cliente = clienteRepository.estrazionePerCodiceFiscale(cf);
            return cliente;
        }
    }
}
