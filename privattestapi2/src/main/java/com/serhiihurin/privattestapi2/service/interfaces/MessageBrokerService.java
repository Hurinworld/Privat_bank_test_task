package com.serhiihurin.privattestapi2.service.interfaces;

import com.serhiihurin.privattestapi2.dto.ClientDataDTO;

public interface MessageBrokerService {
    void receiveMessage(ClientDataDTO clientData);
}
