package com.serhiihurin.privattestapi5.service;

import com.serhiihurin.privattestapi5.dao.CardRepository;
import com.serhiihurin.privattestapi5.entity.Card;
import com.serhiihurin.privattestapi5.service.interfaces.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    public void addCard(Card card) {
        Optional<Card> existingCard = cardRepository.getCardByCardNumber(card.getCardNumber());
        existingCard.ifPresent(value -> card.setId(value.getId()));
        cardRepository.save(card);
    }
}
