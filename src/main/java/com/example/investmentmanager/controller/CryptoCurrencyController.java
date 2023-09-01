package com.example.investmentmanager.controller;

import com.example.investmentmanager.model.CryptoCurrencyDTO;
import com.example.investmentmanager.services.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CryptoCurrencyController {
    private final CryptoCurrencyService cryptoCurrencyService;
    public static final String CRYPTO_PATH = "/api/v1/cryptocurrency";
    public static final String CRYPTO_PATH_ID = CRYPTO_PATH+ "/{cryptoId}";

    @PatchMapping(CRYPTO_PATH_ID)
    public ResponseEntity updateCryptoPatchById(@PathVariable("cryptoId") UUID cryptoId, @RequestBody CryptoCurrencyDTO cryptoCurrency){
        cryptoCurrencyService.patchCryptoById(cryptoId, cryptoCurrency);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(CRYPTO_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("cryptoId") UUID cryptoId){
        cryptoCurrencyService.deleteById(cryptoId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CRYPTO_PATH_ID)
    public ResponseEntity updateById(@PathVariable("cryptoId") UUID cryptoId, @RequestBody CryptoCurrencyDTO cryptoCurrency){

        cryptoCurrencyService.updateCryptoCurrencyById(cryptoId,cryptoCurrency);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(CRYPTO_PATH)
    public ResponseEntity handlePost(@RequestBody CryptoCurrencyDTO cryptoCurrency) {

        CryptoCurrencyDTO savedCrypto = cryptoCurrencyService.saveNewCryptoCurrency(cryptoCurrency);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CRYPTO_PATH +"/"+savedCrypto.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @GetMapping(CRYPTO_PATH)
    public List<CryptoCurrencyDTO> listCryptoCurrencies() {
        return cryptoCurrencyService.listCryptoCurrencies();
    }

    @GetMapping(CRYPTO_PATH_ID)
    public CryptoCurrencyDTO getCryptocurrencyById(@PathVariable("cryptoId") UUID cryptoId){
        return cryptoCurrencyService.getCryptocurrencyById(cryptoId).orElseThrow(NotFoundException::new);
    }
}
