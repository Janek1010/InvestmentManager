package com.example.investmentmanager.controller;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.mappers.CryptoCurrencyMapper;
import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CryptoCurrencyControllerIT {
    @Autowired
    CryptoCurrencyController cryptoCurrencyController;

    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    CryptoCurrencyMapper cryptoCurrencyMapper;

    @Test
    void updateExistingCrypto() {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findAll().get(0);
        CryptoCurrencyDTO cryptoCurrencyDTO = cryptoCurrencyMapper.cryptoToCryptoDto(cryptoCurrency);
        cryptoCurrencyDTO.setId(null);
        cryptoCurrencyDTO.setVersion(null);
        final String cryptoName = "UPDATED";
        cryptoCurrencyDTO.setCryptoCurrencyName(cryptoName);

        ResponseEntity responseEntity = cryptoCurrencyController.updateById(cryptoCurrency.getId(),cryptoCurrencyDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        CryptoCurrency updatedCrypto = cryptoCurrencyRepository.findById(cryptoCurrency.getId()).get();
        assertThat(updatedCrypto.getCryptoCurrencyName()).isEqualTo(cryptoName);
    }

    @Test
    void testListCryptoCurrencies() {
        List<CryptoCurrencyDTO> dtos = cryptoCurrencyController.listCryptoCurrencies();

        assertThat(dtos.size()).isEqualTo(3);
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
        List<CryptoCurrencyDTO> dtos = cryptoCurrencyController.listCryptoCurrencies();

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