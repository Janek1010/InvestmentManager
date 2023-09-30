package com.example.investmentmanager.repositories;

import com.example.investmentmanager.entities.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, UUID> {

    List<CryptoCurrency> findAllByCryptoCurrencyNameIsLikeIgnoreCase(String cryptoName);
}
