package com.example.investmentmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrency {
    @Id
    private UUID id;
    private String cryptoCurrencyName;

    @Version
    private Integer version;
    private BigDecimal price;
    private Double amount;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
