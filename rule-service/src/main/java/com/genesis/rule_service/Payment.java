package com.genesis.rule_service;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private Integer daysLate;
    private BigDecimal amount;
}
