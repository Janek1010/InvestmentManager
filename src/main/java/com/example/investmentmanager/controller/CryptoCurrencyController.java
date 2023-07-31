package com.example.investmentmanager.controller;

import com.example.investmentmanager.model.CryptoCurrency;
import com.example.investmentmanager.services.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class CryptoCurrencyController {
    private final CryptoCurrencyService cryptoCurrencyService;

    @RequestMapping("/api/v1/cryptocurrency")
    public List<CryptoCurrency> listCryptoCurrencies(){
        return cryptoCurrencyService.listCryptoCurrencies();
    }
    public CryptoCurrency getCryptocurrencyById(UUID uuid){
        log.debug("Get CryptoCurrency by Id - in controller");
        return cryptoCurrencyService.getCryptocurrencyById(uuid);
    }
}
