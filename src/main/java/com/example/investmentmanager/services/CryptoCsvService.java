package com.example.investmentmanager.services;

import com.example.investmentmanager.model.CryptoCurrencyCSVRecord;

import java.io.File;
import java.util.List;

public interface CryptoCsvService {
    List<CryptoCurrencyCSVRecord> convertCSV(File csvFile);
}
