package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface CryptoCurrencyService {
    CryptoCurrency getCryptocurrencyById(UUID uuid);
    List<CryptoCurrency> listCryptoCurrencies();
}
