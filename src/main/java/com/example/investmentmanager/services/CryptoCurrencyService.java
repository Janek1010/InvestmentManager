package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrencyDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface CryptoCurrencyService {
    Optional<CryptoCurrencyDTO> getCryptocurrencyById(UUID uuid);
    Page<CryptoCurrencyDTO> listCryptoCurrencies(String cryptoName, Boolean showInventory, Integer pageNumber, Integer pageSize);

    CryptoCurrencyDTO saveNewCryptoCurrency(CryptoCurrencyDTO cryptoCurrency);

    Optional<CryptoCurrencyDTO> updateCryptoCurrencyById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency);

    Boolean deleteById(UUID cryptoId);

    Optional<CryptoCurrencyDTO> patchCryptoById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency);
}
