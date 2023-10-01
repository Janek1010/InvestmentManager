package com.example.investmentmanager.repositories;

import com.example.investmentmanager.entities.CryptoCurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, UUID> {
    Page<CryptoCurrency> findAllByCryptoCurrencyNameIsLikeIgnoreCase(String cryptoName, Pageable pageable);
}
