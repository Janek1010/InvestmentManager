package com.example.investmentmanager.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CryptoCurrencyDTO {
    private UUID id;
    private String cryptoCurrencyName;
    private Integer version;
    private BigDecimal price;
    private Double amount;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}