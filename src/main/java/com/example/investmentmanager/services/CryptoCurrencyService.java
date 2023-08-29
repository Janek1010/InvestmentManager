package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CryptoCurrencyService {
    Optional<CryptoCurrency> getCryptocurrencyById(UUID uuid);
    List<CryptoCurrency> listCryptoCurrencies();

    CryptoCurrency saveNewCryptoCurrency(CryptoCurrency cryptoCurrency);

    void updateCryptoCurrencyById(UUID cryptoId, CryptoCurrency cryptoCurrency);

    void deleteById(UUID cryptoId);

    void patchCryptoById(UUID cryptoId, CryptoCurrency cryptoCurrency);
}
