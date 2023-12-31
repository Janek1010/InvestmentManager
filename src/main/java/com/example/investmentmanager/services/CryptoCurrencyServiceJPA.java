package com.example.investmentmanager.services;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.mappers.CryptoCurrencyMapper;
import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.repositories.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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
    public Page<CryptoCurrencyDTO> listCryptoCurrencies(String cryptoName, Boolean showInventory,
                                                        Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<CryptoCurrency> cryptoPage;

        if (StringUtils.hasText(cryptoName)) {
            cryptoPage = pageCryptoByName(cryptoName, pageRequest);
        } else {
            cryptoPage = cryptoCurrencyRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            cryptoPage.forEach(cryptoCurrency -> cryptoCurrency.setAmount(null));
        }

        return cryptoPage.map(cryptoCurrencyMapper::cryptoToCryptoDto);

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

        Sort sort = Sort.by(Sort.Order.asc("cryptoCurrencyName"));

        return PageRequest.of(queryPageNumber, queryPageSize,sort);
    }

    public Page<CryptoCurrency> pageCryptoByName(String cryptoName, Pageable pageable) {
        return cryptoCurrencyRepository.findAllByCryptoCurrencyNameIsLikeIgnoreCase("%" + cryptoName + "%", pageable);
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
