package com.serhiihurin.privattestapi4.service;

import com.serhiihurin.privattestapi4.dto.CardDTO;
import com.serhiihurin.privattestapi4.dto.ClientDataDTO;
import com.serhiihurin.privattestapi4.dto.ExternalDataDTO;
import com.serhiihurin.privattestapi4.service.interfaces.MessageBrokerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageBrokerServiceImpl implements MessageBrokerService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener(queues = "service4Queue")
    public void receiveMessage(ClientDataDTO clientData) {
        log.info(
                "Received message from service4Queue with client ID: {}, name: {} phone number: {} address: {}",
                clientData.clientId(),
                clientData.fullName(),
                clientData.phoneNumber(),
                clientData.address()
        );
        var externalData = mockExternalService(clientData);
        rabbitTemplate.convertAndSend(
                "exchange",
                "service5Queue",
                externalData
        );
        log.info("Successfully send message to service5Queue");
    }

    private ExternalDataDTO mockExternalService(ClientDataDTO clientData) {
        var cards = new ArrayList<CardDTO>();
        cards.add(
                CardDTO.builder()
                .cardNumber(4441111112344321L)
                .expiryDate("10/28")
                .cvv(514L)
                .build()
        );
        cards.add(
                CardDTO.builder()
                        .cardNumber(4441587412344321L)
                        .expiryDate("12/27")
                        .cvv(590L)
                        .build()
        );
        return ExternalDataDTO.builder()
                .clientId(clientData.clientId())
                .fullName(clientData.fullName())
                .phoneNumber(clientData.phoneNumber())
                .address(clientData.address())
                .cards(cards)
                .build();
    }
}
