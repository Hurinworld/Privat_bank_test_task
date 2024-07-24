package com.serhiihurin.privattestapi3.service;

import com.serhiihurin.privattestapi3.dto.ClientDataDTO;
import com.serhiihurin.privattestapi3.dto.ExternalDataDTO;
import com.serhiihurin.privattestapi3.service.interfaces.MessageBrokerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageBrokerServiceImpl implements MessageBrokerService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener(queues = "service3Queue")
    public void receiveMessage(ClientDataDTO clientData) {
        log.info(
                "Received message from service3queue with client ID: {}, name: {} and phone number: {}",
                clientData.clientId(),
                clientData.fullName(),
                clientData.phoneNumber()
        );
        var externalData = mockExternalService(clientData);
        rabbitTemplate.convertAndSend(
                "exchange",
                "service4Queue",
                externalData
        );
        log.info("Successfully send message to service4Queue");
    }

    private ExternalDataDTO mockExternalService(ClientDataDTO clientData) {
        return ExternalDataDTO.builder()
                .clientId(clientData.clientId())
                .fullName(clientData.fullName())
                .phoneNumber(clientData.phoneNumber())
                .address("Soborna street 37 m.Vinnytsia, 21000")
                .build();
    }
}
