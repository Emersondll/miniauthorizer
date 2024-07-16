package com.vr.miniauthorizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniauthorizer.model.CardModel;
import com.vr.miniauthorizer.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    void testCreateCardSuccess() throws Exception {
        CardModel card = new CardModel("password", "1234567890");

        when(cardService.createCard(any(CardModel.class))).thenReturn(card);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(card);

        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCartao").value("1234567890"))
                .andExpect(jsonPath("$.senha").value("password"));
    }

    @Test
    void testCreateCardFailure() throws Exception {
        CardModel card = new CardModel("1234567890", "password");

        when(cardService.createCard(any(CardModel.class))).thenThrow(new RuntimeException("Error creating card"));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(card);

        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testCheckBalanceSuccess() throws Exception {
        BigDecimal balance = new BigDecimal("1000.00");

        when(cardService.checkBalance("1234567890")).thenReturn(balance);

        mockMvc.perform(get("/cartoes/{cardNumber}", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1000.00"));
    }

    @Test
    void testCheckBalanceNotFound() throws Exception {
        when(cardService.checkBalance("1234567890")).thenThrow(new RuntimeException("Card not found"));

        mockMvc.perform(get("/cartoes/{cardNumber}", "1234567890"))
                .andExpect(status().isNotFound());
    }
}
