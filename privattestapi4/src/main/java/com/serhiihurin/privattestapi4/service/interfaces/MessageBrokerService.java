package com.serhiihurin.privattestapi4.service.interfaces;

import com.serhiihurin.privattestapi4.dto.ClientDataDTO;

public interface MessageBrokerService {
    void receiveMessage(ClientDataDTO clientData);
}
