package com.example.investmentmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class CryptoCurrencyControllerTest {

    @Autowired
    CryptoCurrencyController cryptoCurrencyController;
    @Test
    void getAssetById() {
        System.out.println(cryptoCurrencyController.getCryptocurrencyById(UUID.randomUUID()));
    }
}