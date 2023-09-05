package com.example.investmentmanager.repositories;

import com.example.investmentmanager.entities.CryptoCurrency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CryptoCurrencyRepositoryTest {
    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository;

    @Test
    void testSaveCrypto() {
        CryptoCurrency savedCrypto = cryptoCurrencyRepository.save(CryptoCurrency.builder()
                .cryptoCurrencyName("test crypto")
                .price(new BigDecimal("13212.123"))
                .amount(321.21)
                .build());

        cryptoCurrencyRepository.flush();

        assertThat(savedCrypto).isNotNull();
        assertThat(savedCrypto.getId()).isNotNull();

    }

}