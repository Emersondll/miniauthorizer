package com.vr.miniauthorizer.service.impl;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.exception.CardException;
import com.vr.miniauthorizer.model.CardModel;
import com.vr.miniauthorizer.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import resources.fixtures.TestFixture;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Create Card Success")
    void testCreateCardSuccess() {
        CardModel cardModel = new CardModel(TestFixture.CARD_NUMBER, TestFixture.CARD_PASSWORD);
        Card savedCard = TestFixture.createCard();

        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.empty());
        when(cardRepository.save(any(Card.class))).thenReturn(savedCard);

        CardModel createdCard = cardService.createCard(cardModel);

        assertEquals(cardModel, createdCard);
    }


    @Test
    @DisplayName("Test Create Card Already Exists")
    void testCreateCardAlreadyExists() {
        Card existingCard = TestFixture.createCard();
        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.of(existingCard));

        CardModel cardModel = new CardModel(TestFixture.CARD_PASSWORD, TestFixture.CARD_NUMBER);
        assertThrows(CardException.CardAlreadyExistsException.class, () -> cardService.createCard(cardModel));
    }


    @Test
    @DisplayName("Test Check Balance Success")
    void testCheckBalanceSuccess() {
        Card card = TestFixture.createCard();

        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.of(card));
        BigDecimal balance = cardService.checkBalance(TestFixture.CARD_NUMBER);
        assertEquals(new BigDecimal(TestFixture.CARD_AMOUNT), balance);
    }

    @Test
    @DisplayName("Test Check Balance Card Not Found")
    void testCheckBalanceCardNotFound() {
        when(cardRepository.findById(TestFixture.CARD_NUMBER)).thenReturn(Optional.empty());
        assertThrows(CardException.CardNotFoundException.class, () -> cardService.checkBalance(TestFixture.CARD_NUMBER));
    }


}

