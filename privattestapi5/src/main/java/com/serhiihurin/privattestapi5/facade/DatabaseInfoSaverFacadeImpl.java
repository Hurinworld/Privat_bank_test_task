package com.serhiihurin.privattestapi5.facade;

import com.serhiihurin.privattestapi5.config.RabbitMQConfig;
import com.serhiihurin.privattestapi5.dto.CardDTO;
import com.serhiihurin.privattestapi5.dto.ClientDataDTO;
import com.serhiihurin.privattestapi5.entity.Card;
import com.serhiihurin.privattestapi5.entity.Client;
import com.serhiihurin.privattestapi5.facade.interfaces.DatabaseInfoSaverFacade;
import com.serhiihurin.privattestapi5.service.interfaces.CardService;
import com.serhiihurin.privattestapi5.service.interfaces.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInfoSaverFacadeImpl implements DatabaseInfoSaverFacade {
    private final ClientService clientService;
    private final CardService cardService;

    @Override
    public List<Client> getClients() {
        return clientService.getAllClients();
    }

    @Override
    @Transactional
    @RabbitListener(queues = RabbitMQConfig.queueName)
    public void saveClientData(ClientDataDTO clientDataDto) {
        log.info("Received message from service5Queue with ID: {}", clientDataDto.clientId());
        var client = Client.builder()
                .id(clientDataDto.clientId())
                .fullName(clientDataDto.fullName())
                .phoneNumber(clientDataDto.phoneNumber())
                .address(clientDataDto.address())
                .build();
        log.info("Attempt to save client info to database");
        log.info(String.valueOf(clientDataDto.cards().get(0).cardNumber()));
        clientService.addClient(client);
        for (CardDTO cardDTO : clientDataDto.cards()) {
            cardService.addCard(
                    Card.builder()
                            .cardNumber(cardDTO.cardNumber())
                            .expiryDate(cardDTO.expiryDate())
                            .cvv(cardDTO.cvv())
                            .client(client)
                            .build()
            );
        }
        log.info("Successfully saved client info to database");
    }
}
