package com.example.investmentmanager.controller;

import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.services.CryptoCurrencyService;
import com.example.investmentmanager.services.CryptoCurrencyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CryptoCurrencyDTO> cryptoCurrencyArgumentCaptor;

    CryptoCurrencyServiceImpl cryptoCurrencyServiceImpl;

    @BeforeEach
    void setUp() {
        cryptoCurrencyServiceImpl = new CryptoCurrencyServiceImpl();
    }

    @Test
    void getCryptoByIdNotFound() throws Exception {

        given(cryptoCurrencyService.getCryptocurrencyById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CryptoCurrencyController.CRYPTO_PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPatchCrypto() throws Exception {
        CryptoCurrencyDTO cryptoCurrency = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);

        Map<String, Object> cryptoMap = new HashMap<>();
        cryptoMap.put("cryptoCurrencyName", "New Name");

        mockMvc.perform(patch(CryptoCurrencyController.CRYPTO_PATH_ID, cryptoCurrency.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cryptoMap)))
                .andExpect(status().isNoContent());

        verify(cryptoCurrencyService).patchCryptoById(uuidArgumentCaptor.capture(), cryptoCurrencyArgumentCaptor.capture());

        assertThat(cryptoCurrency.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(cryptoMap.get("cryptoCurrencyName")).isEqualTo(cryptoCurrencyArgumentCaptor.getValue().getCryptoCurrencyName());

    }


    @Test
    void testUpdateCrypto() throws Exception {
        CryptoCurrencyDTO cryptoCurrency = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);

        mockMvc.perform(put(CryptoCurrencyController.CRYPTO_PATH_ID, cryptoCurrency.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cryptoCurrency)))
                .andExpect(status().isNoContent());

        verify(cryptoCurrencyService).updateCryptoCurrencyById(any(UUID.class), any(CryptoCurrencyDTO.class));
    }

    @Test
    void testDeleteCrypto() throws Exception {
        CryptoCurrencyDTO cryptoCurrency = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);

        mockMvc.perform(delete(CryptoCurrencyController.CRYPTO_PATH_ID, cryptoCurrency.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(cryptoCurrencyService).deleteById(uuidArgumentCaptor.capture());

        assertThat(cryptoCurrency.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testCreateNewCrypto() throws Exception {
        CryptoCurrencyDTO cryptoCurrency = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);
        cryptoCurrency.setId(null);

        given(cryptoCurrencyService.saveNewCryptoCurrency(any(CryptoCurrencyDTO.class))).willReturn(cryptoCurrencyServiceImpl.listCryptoCurrencies().get(1));
        mockMvc.perform(post(CryptoCurrencyController.CRYPTO_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cryptoCurrency)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void testListCrypto() throws Exception {
        given(cryptoCurrencyService.listCryptoCurrencies()).willReturn(cryptoCurrencyServiceImpl.listCryptoCurrencies());
        mockMvc.perform(get(CryptoCurrencyController.CRYPTO_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));

    }

    @Test
    void isValidObject() throws Exception {
        CryptoCurrencyDTO testCrypto = cryptoCurrencyServiceImpl.listCryptoCurrencies().get(0);

        given(cryptoCurrencyService.getCryptocurrencyById(testCrypto.getId()))
                .willReturn(Optional.of(testCrypto));

        mockMvc.perform(get(CryptoCurrencyController.CRYPTO_PATH + "/" + testCrypto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCrypto.getId().toString())))
                .andExpect(jsonPath("$.cryptoCurrencyName", is(testCrypto.getCryptoCurrencyName())));
    }
}