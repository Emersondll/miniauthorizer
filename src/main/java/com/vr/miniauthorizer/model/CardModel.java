package com.vr.miniauthorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardModel {

    @JsonProperty("numeroCartao") String cardNumber;
    @JsonProperty("senha") String password;

    public CardModel(final String cardNumber, final String password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPassword() {
        return password;
    }
}
