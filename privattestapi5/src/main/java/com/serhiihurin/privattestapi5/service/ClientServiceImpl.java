package com.serhiihurin.privattestapi5.service;

import com.serhiihurin.privattestapi5.dao.ClientRepository;
import com.serhiihurin.privattestapi5.entity.Client;
import com.serhiihurin.privattestapi5.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public void addClient(Client client) {
        clientRepository.save(client);
    }
}
