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

    /**
     * Handles HTTP POST requests to create a new card.
     *
     * @param card The card model received in the request body.
     * @return ResponseEntity containing the created card model with HTTP status 201 (CREATED) on success.
     * If creation fails due to an exception, returns the original card model with HTTP status 422 (UNPROCESSABLE_ENTITY).
     */
    @PostMapping
    public ResponseEntity<Object> createCard(final @RequestBody CardModel card) {
        try {
            CardModel newCard = service.createCard(card);
            return new ResponseEntity<>(newCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(card, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Handles HTTP GET requests to retrieve the balance of a card identified by its card number.
     *
     * @param cardNumber The card number path variable used to identify the card.
     * @return ResponseEntity containing the balance of the card with HTTP status 200 (OK) on success.
     * If the card is not found or if an exception occurs, returns HTTP status 404 (NOT_FOUND).
     */
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

