package com.vr.miniauthorizer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniauthorizer.model.TransactionModel;
import com.vr.miniauthorizer.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
   void testPerformTransactionSuccess() throws Exception {
      doNothing().when(transactionService).performTransaction(any(TransactionModel.class));
      TransactionModel transaction = new TransactionModel("1234567890", "1234", new BigDecimal("100.00"));

      final String requestBody = writeJson(transaction);

      mockMvc.perform(post("/transacoes")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(requestBody))
              .andExpect(status().isCreated())
              .andExpect(content().string("OK"));
   }

   @Test
   void testPerformTransactionFailure() throws Exception {
      TransactionModel transaction = new TransactionModel("1234567890", "1234", new BigDecimal("100.00"));
      doThrow(new RuntimeException("Insufficient balance")).when(transactionService).performTransaction(any(TransactionModel.class));

      final String requestBody = writeJson(transaction);

      mockMvc.perform(post("/transacoes")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(requestBody))
              .andExpect(status().isUnprocessableEntity()) // Espera que o status retornado seja 422 (UNPROCESSABLE_ENTITY)
              .andExpect(content().string("Insufficient balance")); // Verifica se a mensagem de erro está correta
   }

   private String writeJson(TransactionModel transaction) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      String requestBody = objectMapper.writeValueAsString(transaction);
      return requestBody;
   }
}
