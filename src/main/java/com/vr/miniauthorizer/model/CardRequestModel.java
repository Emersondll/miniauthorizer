package com.vr.miniauthorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardRequestModel{

    @JsonProperty("numeroCartao") String cardNumber;
    @JsonProperty("senha") String cardPassword;

    public CardRequestModel(String cardNumber, String cardPassword) {
        this.cardNumber = cardNumber;
        this.cardPassword = cardPassword;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }
}
