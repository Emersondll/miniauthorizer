package com.vr.miniauthorizer.service.impl;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.exception.BalanceException;
import com.vr.miniauthorizer.exception.CardException;
import com.vr.miniauthorizer.exception.PasswordException;
import com.vr.miniauthorizer.model.TransactionModel;
import com.vr.miniauthorizer.repository.CardRepository;
import com.vr.miniauthorizer.service.TransactionService;
import com.vr.miniauthorizer.utils.ExceptionMessages;
import com.vr.miniauthorizer.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private CardRepository repository;

    @Transactional
    public void performTransaction(final TransactionModel transactionModel) {
        Card card = repository.findById(transactionModel.cardNumber())
                .orElseThrow(() -> new CardException.CardNotFoundException(ExceptionMessages.CARD_NOT_FOUND));

        comparePassword(transactionModel, card);
        checkBalance(transactionModel, card);

        card.setAmount(card.getAmount().subtract(transactionModel.amount()));
        repository.save(card);
    }

    private void checkBalance(TransactionModel transactionModel, Card card) {
        Optional.of(card.getAmount())
                .filter(amount -> amount.compareTo(transactionModel.amount()) >= 0)
                .orElseThrow(() -> new BalanceException(ExceptionMessages.INSUFFICIENT_BALANCE));
    }

    private void comparePassword(TransactionModel transactionModel, Card card) {
        Optional.of(HashUtil.compareHash(transactionModel.cardPassword(), card.getPassword()))
                .filter(isPasswordCorrect -> isPasswordCorrect)
                .orElseThrow(() -> new PasswordException(ExceptionMessages.INVALID_PASSWORD));
    }
}