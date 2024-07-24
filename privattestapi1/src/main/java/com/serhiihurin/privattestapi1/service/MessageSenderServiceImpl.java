package com.serhiihurin.privattestapi1.service;

import com.serhiihurin.privattestapi1.config.RabbitMQConfig;
import com.serhiihurin.privattestapi1.dto.ClientDataDTO;
import com.serhiihurin.privattestapi1.service.interfaces.MessageSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSenderServiceImpl implements MessageSenderService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(ClientDataDTO clientDataDTO) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.directExchangeName,
                RabbitMQConfig.routingKey,
                clientDataDTO
        );
        log.info(
                "Message sent successfully to exchange: {} with routing key: {}",
                RabbitMQConfig.directExchangeName,
                RabbitMQConfig.routingKey
        );
    }
}
