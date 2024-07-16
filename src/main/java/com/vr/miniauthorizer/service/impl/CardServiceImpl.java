package com.vr.miniauthorizer.service.impl;

import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.exception.CardException;
import com.vr.miniauthorizer.model.CardModel;
import com.vr.miniauthorizer.repository.CardRepository;
import com.vr.miniauthorizer.service.CardService;
import com.vr.miniauthorizer.utils.ExceptionMessages;
import com.vr.miniauthorizer.utils.HashUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardServiceImpl implements CardService {

    @Value("${values.standardValue}")
    private BigDecimal initialBalance;
    @Autowired
    private CardRepository repository;

    /**
     * Creates a new card based on the provided card model.
     *
     * @param cardModel The card model containing details of the card to be created.
     * @return The created CardModel object if successful.
     * @throws CardException.CardAlreadyExistsException If a card with the same card number already exists in the repository.
     */
    public CardModel createCard(final CardModel cardModel) {
        if (repository.findById(cardModel.cardNumber()).isPresent()) {
            throw new CardException.CardAlreadyExistsException();
        }
        Card newCard = createNewRegisterDocument(cardModel);
        repository.save(newCard);
        return cardModel;

    }

    /**
     * Creates a new Card entity based on the provided CardModel.
     *
     * @param cardModel The CardModel containing details for the new Card entity.
     * @return A new Card entity initialized with values from the CardModel.
     */
    private Card createNewRegisterDocument(final CardModel cardModel) {
        Card newCard = new Card();
        BeanUtils.copyProperties(cardModel, newCard);
        newCard.setAmount(initialBalance);
        newCard.setPassword(HashUtil.hashString(cardModel.password()));
        return newCard;
    }

    /**
     * Checks the balance for a card based on its card number.
     *
     * @param cardNumber The card number for which the balance is to be checked.
     * @return The current balance of the card.
     * @throws CardException.CardNotFoundException If no card with the specified card number is found in the repository.
     */
    public BigDecimal checkBalance(final String cardNumber) {
        Card card = repository.findById(cardNumber)
                .orElseThrow(() -> new CardException.CardNotFoundException(ExceptionMessages.CARD_NOT_FOUND));
        return card.getAmount();
    }
}
