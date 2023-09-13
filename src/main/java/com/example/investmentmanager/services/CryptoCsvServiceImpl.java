package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrencyCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CryptoCsvServiceImpl implements CryptoCsvService {
    @Override
    public List<CryptoCurrencyCSVRecord> convertCSV(File csvFile) {
        try {
            List<CryptoCurrencyCSVRecord> cryptoCurrencyCSVRecords = new CsvToBeanBuilder<CryptoCurrencyCSVRecord>(new FileReader(csvFile))
            .withType(CryptoCurrencyCSVRecord.class)
                    .build().parse();
            return cryptoCurrencyCSVRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
