package com.example.investmentmanager.services;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.mappers.CryptoCurrencyMapper;
import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CryptoCurrencyServiceJPA implements CryptoCurrencyService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Optional<CryptoCurrencyDTO> getCryptocurrencyById(UUID uuid) {
        return Optional.ofNullable(cryptoCurrencyMapper.cryptoToCryptoDto(cryptoCurrencyRepository.findById(uuid)
                .orElse(null)));
    }

    @Override
    public List<CryptoCurrencyDTO> listCryptoCurrencies(String cryptoName, Boolean showInventory,
                                                        Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        List<CryptoCurrency> cryptoCurrencyList;

        if (StringUtils.hasText(cryptoName)) {
            cryptoCurrencyList = listCryptoByName(cryptoName);
        } else {
            cryptoCurrencyList = cryptoCurrencyRepository.findAll();
        }

        if (showInventory != null && !showInventory) {
            cryptoCurrencyList.forEach(cryptoCurrency -> cryptoCurrency.setAmount(null));
        }

        return cryptoCurrencyList.stream()
                .map(cryptoCurrencyMapper::cryptoToCryptoDto)
                .collect(Collectors.toList());
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    public List<CryptoCurrency> listCryptoByName(String cryptoName) {
        return cryptoCurrencyRepository.findAllByCryptoCurrencyNameIsLikeIgnoreCase("%" + cryptoName + "%");
    }

    @Override
    public CryptoCurrencyDTO saveNewCryptoCurrency(CryptoCurrencyDTO cryptoCurrency) {
        return cryptoCurrencyMapper.cryptoToCryptoDto(cryptoCurrencyRepository
                .save(cryptoCurrencyMapper.cryptoDtoToCrypto(cryptoCurrency)));
    }

    @Override
    public Optional<CryptoCurrencyDTO> updateCryptoCurrencyById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {
        AtomicReference<Optional<CryptoCurrencyDTO>> atomicReference = new AtomicReference<>();

        cryptoCurrencyRepository.findById(cryptoId).ifPresentOrElse(foundCrypto -> {
            foundCrypto.setCryptoCurrencyName(cryptoCurrency.getCryptoCurrencyName());
            foundCrypto.setPrice(cryptoCurrency.getPrice());
            foundCrypto.setAmount(cryptoCurrency.getAmount());
            atomicReference.set(Optional.of(cryptoCurrencyMapper
                    .cryptoToCryptoDto(cryptoCurrencyRepository.save(foundCrypto))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID cryptoId) {
        if (cryptoCurrencyRepository.existsById(cryptoId)) {
            cryptoCurrencyRepository.deleteById(cryptoId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CryptoCurrencyDTO> patchCryptoById(UUID cryptoId, CryptoCurrencyDTO cryptoCurrency) {
        AtomicReference<Optional<CryptoCurrencyDTO>> atomicReference = new AtomicReference<>();

        cryptoCurrencyRepository.findById(cryptoId).ifPresentOrElse(foundCrypto -> {
            if (StringUtils.hasText(cryptoCurrency.getCryptoCurrencyName())) {
                foundCrypto.setCryptoCurrencyName(cryptoCurrency.getCryptoCurrencyName());
            }
            if (cryptoCurrency.getPrice() != null) {
                foundCrypto.setPrice(cryptoCurrency.getPrice());
            }
            if (cryptoCurrency.getAmount() != null) {
                foundCrypto.setAmount(cryptoCurrency.getAmount());
            }
            atomicReference.set(Optional.of(cryptoCurrencyMapper
                    .cryptoToCryptoDto(cryptoCurrencyRepository.save(foundCrypto))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
