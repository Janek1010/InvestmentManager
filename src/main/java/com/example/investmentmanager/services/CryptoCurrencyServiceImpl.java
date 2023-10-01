package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrencyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private Map<UUID, CryptoCurrencyDTO> cryptoMap;

    @Override
    public Page<CryptoCurrencyDTO> listCryptoCurrencies(String cryptoName, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(cryptoMap.values()));
    }

    @Override
    public CryptoCurrencyDTO saveNewCryptoCurrency(CryptoCurrencyDTO cryptoCurrency) {
        CryptoCurrencyDTO savedCrypto = CryptoCurrencyDTO.builder()
                .cryptoCurrencyName(cryptoCurrency.getCryptoCurrencyName())
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .amount(cryptoCurrency.getAmount())
                .price(cryptoCurrency.getPrice())
                .build();

        cryptoMap.put(savedCrypto.getId(),savedCrypto);

        return savedCrypto;
    }

    @Override
    public Optional<CryptoCurrencyDTO> updateCryptoCurrencyById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {
        CryptoCurrencyDTO existing =  cryptoMap.get(cryptoId);
        existing.setCryptoCurrencyName(cryptoCurrency.getCryptoCurrencyName());
        existing.setPrice(cryptoCurrency.getPrice());
        existing.setAmount(cryptoCurrency.getAmount());
        cryptoMap.put(existing.getId(),existing);
        return Optional.of(existing);
    }

    @Override
    public Boolean deleteById(UUID cryptoId) {
        cryptoMap.remove(cryptoId);
        return true;
    }

    @Override
    public Optional<CryptoCurrencyDTO> patchCryptoById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {
        CryptoCurrencyDTO existing = cryptoMap.get(cryptoId);
        if (StringUtils.hasText(cryptoCurrency.getCryptoCurrencyName())){
            existing.setCryptoCurrencyName(cryptoCurrency.getCryptoCurrencyName());
        }
        if (cryptoCurrency.getAmount() != null){
            existing.setAmount(cryptoCurrency.getAmount());
        }
        if (cryptoCurrency.getPrice() != null){
            existing.setPrice(cryptoCurrency.getPrice());
        }
        return Optional.of(existing);
    }

    public CryptoCurrencyServiceImpl() {
        this.cryptoMap = new HashMap<>();
        CryptoCurrencyDTO crypto1 = CryptoCurrencyDTO.builder()
                .id(UUID.randomUUID())
                .cryptoCurrencyName("ETH")
                .amount(12.03)
                .version(1)
                .price(new BigDecimal(1860.5))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        CryptoCurrencyDTO crypto2 = CryptoCurrencyDTO.builder()
                .id(UUID.randomUUID())
                .cryptoCurrencyName("BTC")
                .amount(1.2)
                .version(1)
                .price(new BigDecimal(27643.2))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        CryptoCurrencyDTO crypto3 = CryptoCurrencyDTO.builder()
                .id(UUID.randomUUID())
                .cryptoCurrencyName("LTC")
                .amount(53.1)
                .version(1)
                .price(new BigDecimal(114.02))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        cryptoMap.put(crypto1.getId(), crypto1);
        cryptoMap.put(crypto2.getId(), crypto2);
        cryptoMap.put(crypto3.getId(), crypto3);
    }

    @Override
    public Optional<CryptoCurrencyDTO> getCryptocurrencyById(UUID uuid) {
        return Optional.ofNullable(cryptoMap.get(uuid));
    }
}
