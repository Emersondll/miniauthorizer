package com.vr.miniauthorizer.service;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.model.CardRequestModel;
import com.vr.miniauthorizer.model.CardResponseModel;
import com.vr.miniauthorizer.repository.CardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardService {

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(500.00);
    public static final String CARD_ALREADY_EXISTS = "Card already exists";
    public static final String CARD_NOT_FOUND = "Card not found";

    @Autowired
    private CardRepository repository;

    public CardResponseModel createCard(CardRequestModel cardRequestModel) {
        if (repository.findById(cardRequestModel.getCardNumber()).isPresent()) {
            throw new RuntimeException(CARD_ALREADY_EXISTS);
        }
        Card newCard = new Card();
        BeanUtils.copyProperties(cardRequestModel, newCard);
        newCard.setAmount(INITIAL_BALANCE);
        repository.save(newCard);
        return new CardResponseModel(cardRequestModel.getCardNumber(),cardRequestModel.getCardPassword());

    }

    public BigDecimal getBalance(String cardNumber) {
        Card card = repository.findById(cardNumber)
                .orElseThrow(() -> new RuntimeException(CARD_NOT_FOUND));
        return card.getAmount();
    }
}
