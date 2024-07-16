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

    /**
     * Performs a transaction using the provided transaction model.
     * This method retrieves the card from the repository, verifies the transaction password,
     * checks if the card has sufficient balance, deducts the transaction amount from the card's balance,
     * and saves the updated card back to the repository.
     *
     * @param transactionModel The transaction details including card number, password, and transaction amount.
     * @throws CardException.CardNotFoundException If no card with the specified card number is found in the repository.
     * @throws PasswordException If the provided transaction password does not match the stored password.
     * @throws BalanceException If the card does not have sufficient balance to complete the transaction.
     *
     * @implNote This method is annotated with @Transactional to ensure all operations within
     *           it are executed within a single transaction. If any exception occurs during
     *           the transaction process, all changes will be rolled back automatically,
     *           maintaining data consistency.
     */
    @Transactional
    public void performTransaction(final TransactionModel transactionModel) {
        Card card = repository.findById(transactionModel.cardNumber())
                .orElseThrow(() -> new CardException.CardNotFoundException(ExceptionMessages.CARD_NOT_FOUND));

        comparePassword(transactionModel, card);
        checkBalance(transactionModel, card);

        card.setAmount(card.getAmount().subtract(transactionModel.amount()));
        repository.save(card);
    }

    /**
     * Checks if the card has sufficient balance to perform the transaction.
     *
     * @param transactionModel The transaction details including the amount to be deducted.
     * @param card             The card for which the balance is checked.
     * @throws BalanceException If the card does not have sufficient balance to cover the transaction amount.
     */
    private void checkBalance(TransactionModel transactionModel, Card card) {
        Optional.of(card.getAmount())
                .filter(amount -> amount.compareTo(transactionModel.amount()) >= 0)
                .orElseThrow(() -> new BalanceException(ExceptionMessages.INSUFFICIENT_BALANCE));
    }

    /**
     * Compares the password provided in the transaction model with the stored hashed password of the card.
     * Throws a PasswordException if the passwords do not match.
     *
     * @param transactionModel The transaction model containing the password to compare.
     * @param card             The card whose password is being verified.
     * @throws PasswordException If the provided password does not match the stored hashed password.
     */
    private void comparePassword(TransactionModel transactionModel, Card card) {
        Optional.of(HashUtil.compareHash(transactionModel.cardPassword(), card.getPassword()))
                .filter(isPasswordCorrect -> isPasswordCorrect)
                .orElseThrow(() -> new PasswordException(ExceptionMessages.INVALID_PASSWORD));
    }
}