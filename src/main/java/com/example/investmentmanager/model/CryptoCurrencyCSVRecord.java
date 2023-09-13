package com.example.investmentmanager.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyCSVRecord {
    @CsvBindByName(column = "row")
    private Integer row;

    @CsvBindByName(column = "crypto_name")
    private String crypto_name;

    @CsvBindByName(column = "price_at_buy_date")
    private Double price_at_buy_date;

    @CsvBindByName
    private Float amount;
}
