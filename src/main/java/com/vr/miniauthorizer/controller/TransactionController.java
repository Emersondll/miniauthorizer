package com.vr.miniauthorizer.controller;

import com.vr.miniauthorizer.model.TransactionModel;
import com.vr.miniauthorizer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransactionController {

    @Autowired
    private TransactionService service;

    /**
     * Handles HTTP POST requests to perform a transaction with the provided transaction model.
     *
     * @param transactionModel The transaction model containing details of the transaction to be performed.
     * @return ResponseEntity with a string "OK" and HTTP status 201 (CREATED) on successful transaction.
     * If an exception occurs during transaction processing, returns the exception message
     * with HTTP status 422 (UNPROCESSABLE_ENTITY).
     */
    @PostMapping
    public ResponseEntity<String> performTransaction(final @RequestBody TransactionModel transactionModel) {
        try {
            service.performTransaction(transactionModel);
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}