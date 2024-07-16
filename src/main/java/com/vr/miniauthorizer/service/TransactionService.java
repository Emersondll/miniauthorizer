package com.vr.miniauthorizer.service;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.exception.CardNotFoundException;
import com.vr.miniauthorizer.exception.InsufficientBalanceException;
import com.vr.miniauthorizer.exception.InvalidPasswordException;
import com.vr.miniauthorizer.model.TransactionModel;
import com.vr.miniauthorizer.repository.CardRepository;
import com.vr.miniauthorizer.utils.ExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {


    @Autowired
    private CardRepository repository;

    @Transactional
    public void performTransaction(final TransactionModel transactionModel) {
        Card card = repository.findById(transactionModel.cardNumber())
                .orElseThrow(() -> new CardNotFoundException(ExceptionMessages.CARD_NOT_FOUND));

        if (!card.getPassword().equals(transactionModel.cardPassword())) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }

        if (card.getAmount().compareTo(transactionModel.amount()) < 0) {
            throw new InsufficientBalanceException(ExceptionMessages.INSUFFICIENT_BALANCE);
        }

        card.setAmount(card.getAmount().subtract(transactionModel.amount()));
        repository.save(card);
    }
}