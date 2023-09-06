package com.example.investmentmanager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CryptoCurrencyDTO {
    private UUID id;
    private Integer version;

    @NotBlank
    @NotNull
    private String cryptoCurrencyName;

    @NotNull
    private BigDecimal price;

    private Double amount;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
