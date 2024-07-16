package resources.fixtures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniauthorizer.document.Card;
import com.vr.miniauthorizer.model.TransactionModel;

import java.math.BigDecimal;

public class TestFixture {
    public static final String CARD_NUMBER = "1234567890";
    public static final String CARD_PASSWORD = "41feds4316sd*$@dfs!";
    public static final String CARD_PASSWORD_HASH = "MEGfTPMhBPRFStXbZHAUT0jFoAWrsA35l/XHgqfPU3E=";
    public static final String CARD_AMOUNT = "500.00";
    public static final String CARD_AMOUNT_HIGH = "500000.00";

    public static Card createCard() {
        Card existingCard = new Card();
        existingCard.setPassword(TestFixture.CARD_PASSWORD);
        existingCard.setCardNumber(TestFixture.CARD_NUMBER);
        existingCard.setAmount(new BigDecimal(TestFixture.CARD_AMOUNT));
        return existingCard;
    }

    public static String writeJson(final TransactionModel transaction) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(transaction);
    }
}
