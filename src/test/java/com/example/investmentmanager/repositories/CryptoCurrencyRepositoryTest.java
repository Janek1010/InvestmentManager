package com.example.investmentmanager.repositories;

import com.example.investmentmanager.bootstrap.BootStrapData;
import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.services.CryptoCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootStrapData.class, CryptoCsvServiceImpl.class})
class CryptoCurrencyRepositoryTest {
    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository;

    @Test
    void testGetCryptoListByName(){
        Page<CryptoCurrency> list = cryptoCurrencyRepository.findAllByCryptoCurrencyNameIsLikeIgnoreCase("%ETH%", null);

        assertThat(list.getContent().size()).isEqualTo(100);

    }

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