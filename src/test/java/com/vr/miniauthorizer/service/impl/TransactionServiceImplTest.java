package com.vr.miniauthorizer.service.impl;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.exception.BalanceException;
import com.vr.miniauthorizer.exception.CardException;
import com.vr.miniauthorizer.exception.PasswordException;
import com.vr.miniauthorizer.model.TransactionModel;
import com.vr.miniauthorizer.repository.CardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import resources.fixtures.TestFixture;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    public TransactionServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Perform Transaction Success")
    void testPerformTransactionSuccess() {

        Card card = TestFixture.createCard();
        card.setPassword(TestFixture.CARD_PASSWORD_HASH);

        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.of(card));

        TransactionModel transactionModel = new TransactionModel(TestFixture.CARD_NUMBER, TestFixture.CARD_PASSWORD, new BigDecimal(TestFixture.CARD_AMOUNT));

        assertDoesNotThrow(() -> transactionService.performTransaction(transactionModel));
    }

    @Test
    @DisplayName("Test Perform Transaction Card Not Found")
    void testPerformTransactionCardNotFound() {

        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.empty());

        TransactionModel transactionModel = new TransactionModel(TestFixture.CARD_NUMBER, TestFixture.CARD_PASSWORD, new BigDecimal(TestFixture.CARD_AMOUNT));

        assertThrows(CardException.CardNotFoundException.class, () -> transactionService.performTransaction(transactionModel));
    }

    @Test
    @DisplayName("Test Perform Transaction Negative Balance")
    void testPerformTransactionNegativeBalance() {
        Card card = TestFixture.createCard();
        card.setPassword(TestFixture.CARD_PASSWORD_HASH);
        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.of(card));

        TransactionModel transactionModel = new TransactionModel(TestFixture.CARD_NUMBER, TestFixture.CARD_PASSWORD, new BigDecimal(TestFixture.CARD_AMOUNT_HIGH));

        assertThrows(BalanceException.class, () -> transactionService.performTransaction(transactionModel));
    }

    @Test
    @DisplayName("Test Perform Password Exception")
    void testPerformPasswordException() {
        Card card = TestFixture.createCard();
        card.setPassword(TestFixture.CARD_PASSWORD);
        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.of(card));

        TransactionModel transactionModel = new TransactionModel(TestFixture.CARD_NUMBER, TestFixture.CARD_PASSWORD, new BigDecimal(TestFixture.CARD_AMOUNT_HIGH));

        assertThrows(PasswordException.class, () -> transactionService.performTransaction(transactionModel));
    }


}
