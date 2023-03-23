package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class DealRequest {

    @NotBlank
    @Id
    private Long dealUniqueId;

    @Size(min = 3, max = 3)
    private String fromCurrencyIsoCode;

    @Size(min = 3, max = 3)
    private String toCurrencyIsoCode;

    private LocalDateTime dealTimestamp;

    @DecimalMin("0")
    private BigDecimal dealAmount;


    public DealRequest(Long dealUniqueId, String fromCurrencyIsoCode, String toCurrencyIsoCode, LocalDateTime dealTimestamp , BigDecimal dealAmount) {
        this.dealUniqueId = dealUniqueId;
        this.fromCurrencyIsoCode= fromCurrencyIsoCode;
        this.toCurrencyIsoCode= toCurrencyIsoCode;
        this.dealTimestamp = dealTimestamp;
        this.dealAmount=dealAmount;

    }
}

