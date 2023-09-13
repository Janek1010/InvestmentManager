package com.example.investmentmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyCSVRecord {
    private String crypto_name;
    private Double price_at_buy_date;
    private LocalDateTime date_of_buy;
    private Float amount;
    private Integer transaction_id;
}
