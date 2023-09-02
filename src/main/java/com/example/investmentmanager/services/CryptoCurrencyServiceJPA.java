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
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CryptoCurrencyServiceJPA implements CryptoCurrencyService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;
    @Override
    public Optional<CryptoCurrencyDTO> getCryptocurrencyById(UUID uuid) {
        return Optional.ofNullable(cryptoCurrencyMapper.cryptoToCryptoDto(cryptoCurrencyRepository.findById(uuid)
                .orElse(null)));
    }

    @Override
    public List<CryptoCurrencyDTO> listCryptoCurrencies() {
        return cryptoCurrencyRepository.findAll()
                .stream()
                .map(cryptoCurrencyMapper::cryptoToCryptoDto)
                .collect(Collectors.toList());
    }

    @Override
    public CryptoCurrencyDTO saveNewCryptoCurrency(CryptoCurrencyDTO cryptoCurrency) {
        return cryptoCurrencyMapper.cryptoToCryptoDto(cryptoCurrencyRepository
                .save(cryptoCurrencyMapper.cryptoDtoToCrypto(cryptoCurrency)));
    }

    @Override
    public void updateCryptoCurrencyById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {
        cryptoCurrencyRepository.findById(cryptoId).ifPresent(foundCrypto -> {
            foundCrypto.setCryptoCurrencyName(cryptoCurrency.getCryptoCurrencyName());
            foundCrypto.setPrice(cryptoCurrency.getPrice());
            foundCrypto.setAmount(cryptoCurrency.getAmount());
            cryptoCurrencyRepository.save(foundCrypto);
        });
    }

    @Override
    public void deleteById(UUID cryptoId) {

    }

    @Override
    public void patchCryptoById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {

    }
}
