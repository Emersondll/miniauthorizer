package com.vr.miniauthorizer.service;

import com.vr.miniauthorizer.model.CardModel;

import java.math.BigDecimal;

public interface CardService {
    CardModel createCard(CardModel cardModel);

    BigDecimal checkBalance(String cardNumber);
}
