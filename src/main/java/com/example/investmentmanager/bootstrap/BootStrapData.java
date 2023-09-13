package com.example.investmentmanager.bootstrap;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.model.CryptoCurrencyCSVRecord;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import com.example.investmentmanager.services.CryptoCsvService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {
    private final CryptoCurrencyRepository cryptoRepository;
    private final CryptoCsvService cryptoCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadcsvData();
    }

    private void loadcsvData() throws FileNotFoundException {
        if (cryptoRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/crypto_transactions.csv");

            List<CryptoCurrencyCSVRecord> recs = cryptoCsvService.convertCSV(file);

            recs.forEach(cryptoCurrencyCSVRecord -> {
                cryptoRepository.save(CryptoCurrency.builder()
                        .cryptoCurrencyName(cryptoCurrencyCSVRecord.getCrypto_name())
                        .amount(Double.valueOf(cryptoCurrencyCSVRecord.getAmount()))
                        .price(BigDecimal.valueOf(cryptoCurrencyCSVRecord.getPrice_at_buy_date()))
                        .build());
            });


        }
    }

    private void loadBeerData() {
        if (cryptoRepository.count() == 0) {
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
