package com.example.investmentmanager.services;

import com.example.investmentmanager.mappers.CryptoCurrencyMapper;
import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CryptoCurrencyServiceJPA implements CryptoCurrencyService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;
    @Override
    public Optional<CryptoCurrencyDTO> getCryptocurrencyById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<CryptoCurrencyDTO> listCryptoCurrencies() {
        return null;
    }

    @Override
    public CryptoCurrencyDTO saveNewCryptoCurrency(CryptoCurrencyDTO cryptoCurrency) {
        return null;
    }

    @Override
    public void updateCryptoCurrencyById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {

    }

    @Override
    public void deleteById(UUID cryptoId) {

    }

    @Override
    public void patchCryptoById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {

    }
}
