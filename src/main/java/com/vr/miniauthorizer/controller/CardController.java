package com.vr.miniauthorizer.controller;

import com.vr.miniauthorizer.model.CardModel;
import com.vr.miniauthorizer.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CardController {

    @Autowired
    private CardService service;

    @PostMapping
    public ResponseEntity<Object> createCard(final @RequestBody CardModel card) {
        try {
            CardModel newCard = service.createCard(card);
            return new ResponseEntity<>(newCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(card, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<BigDecimal> checkBalance(final @PathVariable String cardNumber) {
        try {
            BigDecimal balance = service.checkBalance(cardNumber);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

