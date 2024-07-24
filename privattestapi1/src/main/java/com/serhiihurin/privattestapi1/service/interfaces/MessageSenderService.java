package com.serhiihurin.privattestapi1.service.interfaces;

import com.serhiihurin.privattestapi1.dto.ClientDataDTO;

public interface MessageSenderService {
    void sendMessage(ClientDataDTO clientDataDTO);
}
