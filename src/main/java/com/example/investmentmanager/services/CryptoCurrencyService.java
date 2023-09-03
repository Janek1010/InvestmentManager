package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrencyDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CryptoCurrencyService {
    Optional<CryptoCurrencyDTO> getCryptocurrencyById(UUID uuid);
    List<CryptoCurrencyDTO> listCryptoCurrencies();

    CryptoCurrencyDTO saveNewCryptoCurrency(CryptoCurrencyDTO cryptoCurrency);

    Optional<CryptoCurrencyDTO> updateCryptoCurrencyById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency);

    void deleteById(UUID cryptoId);

    void patchCryptoById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency);
}
