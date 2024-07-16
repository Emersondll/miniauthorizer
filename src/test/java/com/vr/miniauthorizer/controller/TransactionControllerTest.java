package com.vr.miniauthorizer.controller;

import com.vr.miniauthorizer.model.TransactionModel;
import com.vr.miniauthorizer.service.TransactionService;
import com.vr.miniauthorizer.utils.ExceptionMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import resources.fixtures.TestFixture;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    @DisplayName("Test Perform Transaction Success")
    void testPerformTransactionSuccess() throws Exception {
        doNothing().when(transactionService).performTransaction(any(TransactionModel.class));
        TransactionModel transaction = new TransactionModel(TestFixture.CARD_NUMBER, TestFixture.CARD_PASSWORD, new BigDecimal(TestFixture.CARD_AMOUNT));

        final String requestBody = TestFixture.writeJson(transaction);

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("OK"));
    }

    @Test
    @DisplayName("Test Perform Transaction Failure")
    void testPerformTransactionFailure() throws Exception {
        TransactionModel transaction = new TransactionModel(TestFixture.CARD_NUMBER, TestFixture.CARD_PASSWORD, new BigDecimal(TestFixture.CARD_AMOUNT));
        doThrow(new RuntimeException(ExceptionMessages.INSUFFICIENT_BALANCE)).when(transactionService).performTransaction(any(TransactionModel.class));

        final String requestBody = TestFixture.writeJson(transaction);

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ExceptionMessages.INSUFFICIENT_BALANCE));
    }


}
