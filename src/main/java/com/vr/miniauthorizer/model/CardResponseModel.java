package com.vr.miniauthorizer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponseModel extends CardRequestModel  {


    public CardResponseModel(String cardNumber, String cardPassword) {
        super(cardNumber, cardPassword);
    }
    @JsonProperty("valor") BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
