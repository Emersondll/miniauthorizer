package com.vr.miniauthorizer.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record TransactionModel(@JsonProperty("numeroCartao") String cardNumber,
                               @JsonProperty("senha") String cardPassword,
                               @JsonProperty("BigDecimal") BigDecimal amount) {


}
