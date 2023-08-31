package com.example.investmentmanager.bootstrap;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {
    private final CryptoCurrencyRepository cryptoRepository;
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
    }

    private void loadBeerData() {
        if (cryptoRepository.count() == 0){
            CryptoCurrency crypto1 = CryptoCurrency.builder()
                    .cryptoCurrencyName("ETH")
                    .amount(32.2)
                    .version(1)
                    .price(BigDecimal.valueOf(1234))
                    .build();
            CryptoCurrency crypto2 = CryptoCurrency.builder()
                    .cryptoCurrencyName("BTC")
                    .amount(2.2)
                    .version(1)
                    .price(BigDecimal.valueOf(5658))
                    .build();
            CryptoCurrency crypto3 = CryptoCurrency.builder()
                    .cryptoCurrencyName("LTC")
                    .amount(123.3)
                    .version(1)
                    .price(BigDecimal.valueOf(1283))
                    .build();


            cryptoRepository.save(crypto1);
            cryptoRepository.save(crypto2);
            cryptoRepository.save(crypto3);
        }

    }
}
