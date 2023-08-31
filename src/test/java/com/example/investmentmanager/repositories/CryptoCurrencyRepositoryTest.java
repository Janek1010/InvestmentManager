package com.example.investmentmanager.repositories;

import com.example.investmentmanager.entities.CryptoCurrency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CryptoCurrencyRepositoryTest {
    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository;

    @Test
    void testSaveCrypto(){
        CryptoCurrency savedCrypto = cryptoCurrencyRepository.save(CryptoCurrency.builder()
                .cryptoCurrencyName("test crypto")
                .build());
        assertThat(savedCrypto).isNotNull();
        assertThat(savedCrypto.getId()).isNotNull();

    }

}