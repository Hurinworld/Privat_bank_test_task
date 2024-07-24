package com.serhiihurin.privattestapi3.service.interfaces;

import com.serhiihurin.privattestapi3.dto.ClientDataDTO;

public interface MessageBrokerService {
    void receiveMessage(ClientDataDTO clientData);
}
