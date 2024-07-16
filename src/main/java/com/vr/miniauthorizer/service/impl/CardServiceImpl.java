package com.vr.miniauthorizer.service.impl;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.exception.CardException;
import com.vr.miniauthorizer.model.CardModel;
import com.vr.miniauthorizer.repository.CardRepository;
import com.vr.miniauthorizer.service.CardService;
import com.vr.miniauthorizer.utils.ExceptionMessages;
import com.vr.miniauthorizer.utils.HashUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardServiceImpl implements CardService {

    @Value("${values.standardValue}")
    private BigDecimal initialBalance;
    @Autowired
    private CardRepository repository;

    public CardModel createCard(final CardModel cardModel) {
        if (repository.findById(cardModel.cardNumber()).isPresent()) {
            throw new CardException.CardAlreadyExistsException();
        }
        Card newCard = createNewRegisterDocument(cardModel);
        repository.save(newCard);
        return cardModel;

    }

    private Card createNewRegisterDocument(final CardModel cardModel) {
        Card newCard = new Card();
        BeanUtils.copyProperties(cardModel, newCard);
        newCard.setAmount(initialBalance);
        newCard.setPassword(HashUtil.hashString(cardModel.password()));
        return newCard;
    }

    public BigDecimal checkBalance(final String cardNumber) {
        Card card = repository.findById(cardNumber)
                .orElseThrow(() -> new CardException.CardNotFoundException(ExceptionMessages.CARD_NOT_FOUND));
        return card.getAmount();
    }
}
