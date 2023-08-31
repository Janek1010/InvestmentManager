package com.example.investmentmanager.controller;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions.*;
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

    @Test
    void testListCryptoCurrencies() {
        List<CryptoCurrencyDTO> dtos = cryptoCurrencyController.listCryptoCurrencies();

        assertThat(dtos.size()).isEqualTo(3);
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