package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstraiClientiService {
    private final ClienteRepository clienteRepository;

    public EstraiClientiService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> estraiTuttiIClienti() {
        return clienteRepository.estraiTuttiIClienti();
    }
}
