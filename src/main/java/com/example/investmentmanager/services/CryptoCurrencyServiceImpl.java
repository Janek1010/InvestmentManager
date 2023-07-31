package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private Map<UUID, CryptoCurrency> cryptoMap;

    @Override
    public List<CryptoCurrency> listCryptoCurrencies() {
        return new ArrayList<>(cryptoMap.values());
    }

    public CryptoCurrencyServiceImpl() {
        this.cryptoMap = new HashMap<>();
        CryptoCurrency crypto1 = CryptoCurrency.builder()
                .id(UUID.randomUUID())
                .CryptoCurrencyName("ETH")
                .amount(12.03)
                .price(new BigDecimal(1860.5))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        CryptoCurrency crypto2 = CryptoCurrency.builder()
                .id(UUID.randomUUID())
                .CryptoCurrencyName("BTC")
                .amount(1.2)
                .price(new BigDecimal(27643.2))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        CryptoCurrency crypto3 = CryptoCurrency.builder()
                .id(UUID.randomUUID())
                .CryptoCurrencyName("LTC")
                .amount(53.1)
                .price(new BigDecimal(114.02))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        cryptoMap.put(crypto1.getId(), crypto1);
        cryptoMap.put(crypto2.getId(), crypto2);
        cryptoMap.put(crypto3.getId(), crypto3);
    }

    @Override
    public CryptoCurrency getCryptocurrencyById(UUID uuid) {
        log.debug("Get CryptoCurrency Id in service was called");
        return cryptoMap.get(uuid);
    }
}
