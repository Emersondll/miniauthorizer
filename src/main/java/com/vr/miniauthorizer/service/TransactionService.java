package com.vr.miniauthorizer.service;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.model.TransactionModel;
import com.vr.miniauthorizer.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String INSUFFICIENT_BALANCE = "Insufficient balance";
    public static final String CARD_NOT_FOUND = "Card not found";
    @Autowired
    private CardRepository repository;

    @Transactional
    public void performTransaction(TransactionModel transactionModel) {
        Card card = repository.findById(transactionModel.cardNumber())
                .orElseThrow(() -> new RuntimeException(CARD_NOT_FOUND));

        if (!card.getPassword().equals(transactionModel.cardPassword())) {
            throw new RuntimeException(INVALID_PASSWORD);
        }

        if (card.getAmount().compareTo(transactionModel.amount()) < 0) {
            throw new RuntimeException(INSUFFICIENT_BALANCE);
        }

        card.setAmount(card.getAmount().subtract(transactionModel.amount()));
        repository.save(card);
    }
}