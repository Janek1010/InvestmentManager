package com.example.investmentmanager.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36,columnDefinition = "varchar", updatable = false,nullable = false)
    private UUID id;

    private String cryptoCurrencyName;

    @Version
    private Integer version;
    private BigDecimal price;
    private Double amount;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
