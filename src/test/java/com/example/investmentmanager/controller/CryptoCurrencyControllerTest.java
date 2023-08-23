package com.example.investmentmanager.controller;

import com.example.investmentmanager.model.CryptoCurrency;
import com.example.investmentmanager.services.CryptoCurrencyService;
import com.example.investmentmanager.services.CryptoCurrencyServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(CryptoCurrencyController.class)
class CryptoCurrencyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CryptoCurrencyService cryptoCurrencyService;

    CryptoCurrencyServiceImpl cryptoCurrencyServiceImpl;

    @BeforeEach
    void setUp() {
        cryptoCurrencyServiceImpl = new CryptoCurrencyServiceImpl();
    }

    @Test
    void testUpdateCrypto() throws Exception {
        CryptoCurrency cryptoCurrency = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);

        mockMvc.perform(put("/api/v1/cryptocurrency/"+ cryptoCurrency.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cryptoCurrency)))
                .andExpect(status().isNoContent());

        verify(cryptoCurrencyService).updateCryptoCurrencyById(any(UUID.class),any(CryptoCurrency.class));
    }

    @Test
    void testDeleteCrypto() throws Exception {
        CryptoCurrency cryptoCurrency = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);

        mockMvc.perform(delete("/api/v1/cryptocurrency/" + cryptoCurrency.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(cryptoCurrencyService).deleteById(uuidArgumentCaptor.capture());

        assertThat(cryptoCurrency.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }
    @Test
    void testCreateNewCrypto() throws Exception {
        CryptoCurrency cryptoCurrency = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);
        cryptoCurrency.setId(null);

        given(cryptoCurrencyService.saveNewCryptoCurrency(any(CryptoCurrency.class))).willReturn(cryptoCurrencyServiceImpl.listCryptoCurrencies().get(1));
        mockMvc.perform(post("/api/v1/cryptocurrency")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cryptoCurrency)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void testListCrypto() throws Exception {
        given(cryptoCurrencyService.listCryptoCurrencies()).willReturn(cryptoCurrencyServiceImpl.listCryptoCurrencies());
        mockMvc.perform(get("/api/v1/cryptocurrency")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));

    }
    @Test
    void isValidObject() throws Exception {
        CryptoCurrency testCrypto = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);

        given(cryptoCurrencyService.getCryptocurrencyById(testCrypto.getId()))
                .willReturn(testCrypto);

        mockMvc.perform(get("/api/v1/cryptocurrency/" + testCrypto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testCrypto.getId().toString())))
                .andExpect(jsonPath("$.cryptoCurrencyName",is(testCrypto.getCryptoCurrencyName())));
    }
}