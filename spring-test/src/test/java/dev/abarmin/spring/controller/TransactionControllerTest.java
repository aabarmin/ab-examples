package dev.abarmin.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.balance.common.model.Money;
import dev.abarmin.spring.model.AuthorisationRequest;
import dev.abarmin.spring.model.AuthorisationResponse;
import dev.abarmin.spring.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TransactionControllerTest {
    @MockBean
    TransactionService transactionService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getTransactions_shouldReturnList() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.transactions").isArray())
                .andDo(print());

        verify(transactionService).findAll();
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    void saveTransactions_shouldReturnHeaderWithSaved() throws Exception {
        Money amount = Money.of(10, "GBP");
        String requestBody = objectMapper
                .writeValueAsString(new AuthorisationRequest(1, 2, amount));

        when(transactionService.authorise(any(AuthorisationRequest.class)))
                .thenReturn(new AuthorisationResponse(1L, Money.of(10, "GBP")));

        mockMvc.perform(
                        post("/transactions")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith("/transactions/1")));
    }

    @Test
    void saveTransactions_shouldReturnBadRequest_whenBodyIsNotCorrect() throws Exception {
        mockMvc.perform(post("/transactions")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content("[]"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}