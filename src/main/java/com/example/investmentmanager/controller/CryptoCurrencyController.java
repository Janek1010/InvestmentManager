package com.example.investmentmanager.controller;

import com.example.investmentmanager.model.CryptoCurrency;
import com.example.investmentmanager.services.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CryptoCurrencyController {
    private final CryptoCurrencyService cryptoCurrencyService;
    private final String path = "/api/v1/cryptocurrency";
    private final String pathId = "/api/v1/cryptocurrency/{cryptoId}";

    @PatchMapping(pathId)
    public ResponseEntity updateCryptoPatchById(@PathVariable("cryptoId") UUID cryptoId, @RequestBody CryptoCurrency cryptoCurrency){
        cryptoCurrencyService.patchCryptoById(cryptoId, cryptoCurrency);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(pathId)
    public ResponseEntity deleteById(@PathVariable("cryptoId") UUID cryptoId){
        cryptoCurrencyService.deleteById(cryptoId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(pathId)
    public ResponseEntity updateById(@PathVariable("cryptoId") UUID cryptoId, @RequestBody CryptoCurrency cryptoCurrency){

        cryptoCurrencyService.updateCryptoCurrencyById(cryptoId,cryptoCurrency);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(path)
    public ResponseEntity handlePost(@RequestBody CryptoCurrency cryptoCurrency) {

        CryptoCurrency savedCrypto = cryptoCurrencyService.saveNewCryptoCurrency(cryptoCurrency);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", path +savedCrypto.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @GetMapping(path)
    public List<CryptoCurrency> listCryptoCurrencies() {
        return cryptoCurrencyService.listCryptoCurrencies();
    }

    @GetMapping(pathId)
    public CryptoCurrency getCryptocurrencyById(@PathVariable("cryptoId") UUID cryptoId) {
        log.debug("Get CryptoCurrency by Id - in controller");
        return cryptoCurrencyService.getCryptocurrencyById(cryptoId);
    }
}
