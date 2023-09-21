package com.example.investmentmanager.controller;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.mappers.CryptoCurrencyMapper;
import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@SpringBootTest
class CryptoCurrencyControllerIT {
    @Autowired
    CryptoCurrencyController cryptoCurrencyController;

    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    CryptoCurrencyMapper cryptoCurrencyMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testListCryptoByName() throws Exception {
         mockMvc.perform(get(CryptoCurrencyController.CRYPTO_PATH)
                 .queryParam("cryptoCurrencyName","ETH"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.size()",is(100)));
    }

    @Test
    void testPatchCryptoBadName() throws Exception {
        CryptoCurrency beer = cryptoCurrencyRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("cryptoCurrencyName", "New Name 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        MvcResult result =  mockMvc.perform(patch(CryptoCurrencyController.CRYPTO_PATH_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Transactional
    @Rollback
    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            cryptoCurrencyController.deleteById(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findAll().get(0);
        ResponseEntity responseEntity = cryptoCurrencyController.deleteById(cryptoCurrency.getId());

        assertThat(cryptoCurrencyRepository.findById(cryptoCurrency.getId()).isEmpty());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            cryptoCurrencyController.updateById(UUID.randomUUID(), CryptoCurrencyDTO.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingCrypto() {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findAll().get(0);
        CryptoCurrencyDTO cryptoCurrencyDTO = cryptoCurrencyMapper.cryptoToCryptoDto(cryptoCurrency);
        cryptoCurrencyDTO.setId(null);
        cryptoCurrencyDTO.setVersion(null);
        final String cryptoName = "UPDATED";
        cryptoCurrencyDTO.setCryptoCurrencyName(cryptoName);

        ResponseEntity responseEntity = cryptoCurrencyController.updateById(cryptoCurrency.getId(), cryptoCurrencyDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        CryptoCurrency updatedCrypto = cryptoCurrencyRepository.findById(cryptoCurrency.getId()).get();
        assertThat(updatedCrypto.getCryptoCurrencyName()).isEqualTo(cryptoName);
    }

    @Test
    void testListCryptoCurrencies() {
        List<CryptoCurrencyDTO> dtos = cryptoCurrencyController.listCryptoCurrencies(null);

        assertThat(dtos.size()).isEqualTo(1003);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCryptoTest() {
        CryptoCurrencyDTO cryptoDto = CryptoCurrencyDTO.builder()
                .cryptoCurrencyName("New Crypto")
                .build();

        ResponseEntity responseEntity = cryptoCurrencyController.handlePost(cryptoDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findById(savedUUID).get();
        assertThat(cryptoCurrency).isNotNull();
    }

    @Test
    @Rollback
    @Transactional
    void testEmptyList() {
        cryptoCurrencyRepository.deleteAll();
        List<CryptoCurrencyDTO> dtos = cryptoCurrencyController.listCryptoCurrencies(null);

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetById() {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findAll().get(0);

        CryptoCurrencyDTO dto = cryptoCurrencyController.getCryptocurrencyById(cryptoCurrency.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testCryptoIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            cryptoCurrencyController.getCryptocurrencyById(UUID.randomUUID());
        });
    }
}