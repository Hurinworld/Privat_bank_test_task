package com.serhiihurin.privattestapi5.service.interfaces;

import com.serhiihurin.privattestapi5.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    void addClient(Client client);
}
