package com.example.investmentmanager.repositories;

import com.example.investmentmanager.entities.CryptoCurrency;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @Test
    void testCryptoTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            CryptoCurrency savedCrypto = cryptoCurrencyRepository.save(CryptoCurrency.builder()
                    .cryptoCurrencyName("test cryptodsaasadq 12e 2edass dasd 12dasdasdasdwawwadadwad12124141242141241212412412de1d12d12d21d12d12d2314")
                    .price(new BigDecimal("13212.123"))
                    .amount(321.21)
                    .build());

            cryptoCurrencyRepository.flush();
        });

    }
}