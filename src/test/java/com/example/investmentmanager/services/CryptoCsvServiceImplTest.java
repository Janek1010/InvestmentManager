package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrencyCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CryptoCsvServiceImplTest {

    CryptoCsvService cryptoCsvService = new CryptoCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException{
        File file = ResourceUtils.getFile("classpath:csvdata/crypto_transactions.csv");
        List<CryptoCurrencyCSVRecord> recs = cryptoCsvService.convertCSV(file);
        System.out.println(recs.size());
        assertThat(recs.size()).isGreaterThan(0);
    }

}