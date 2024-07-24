package com.serhiihurin.privattestapi2.service;

import com.serhiihurin.privattestapi2.dto.ClientDataDTO;
import com.serhiihurin.privattestapi2.dto.ExternalDataDTO;
import com.serhiihurin.privattestapi2.service.interfaces.MessageBrokerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service("MessageBrokerService")
@RequiredArgsConstructor
@Slf4j
public class MessageBrokerServiceImpl implements MessageBrokerService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener(queues = "service2Queue")
    public void receiveMessage(ClientDataDTO clientData) {
        log.info("Received data from service2Queue. Client ID: {}", clientData.clientId());
        var externalData = mockExternalService(clientData);
        rabbitTemplate.convertAndSend(
                "exchange",
                "service3Queue",
                externalData
        );
        log.info("Successfully send message to service3Queue");
    }

    private ExternalDataDTO mockExternalService(ClientDataDTO clientData) {
        return ExternalDataDTO.builder()
                .clientId(clientData.clientId())
                .fullName("Ivanov Ivan Ivanovych")
                .phoneNumber("+380676516511")
                .build();
    }
}
