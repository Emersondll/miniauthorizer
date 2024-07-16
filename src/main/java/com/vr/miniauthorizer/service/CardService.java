package com.vr.miniauthorizer.service;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.exception.CardAlreadyExistsException;
import com.vr.miniauthorizer.exception.CardNotFoundException;
import com.vr.miniauthorizer.model.CardModel;
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

    public CardModel createCard(final CardModel cardModel) {
        if (repository.findById(cardModel.getCardNumber()).isPresent()) {
            throw new CardAlreadyExistsException(CARD_ALREADY_EXISTS);
        }
        Card newCard = new Card();
        BeanUtils.copyProperties(cardModel, newCard);
        newCard.setAmount(INITIAL_BALANCE);
        repository.save(newCard);
        return cardModel;

    }

    public BigDecimal checkBalance(final String cardNumber) {
        Card card = repository.findById(cardNumber)
                .orElseThrow(() -> new CardNotFoundException(CARD_NOT_FOUND));
        return card.getAmount();
    }
}
