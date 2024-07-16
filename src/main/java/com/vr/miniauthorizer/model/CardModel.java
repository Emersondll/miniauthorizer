package com.vr.miniauthorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardModel(@JsonProperty("senha")
                        String password,
                        @JsonProperty("numeroCartao")
                        String cardNumber) {
}
