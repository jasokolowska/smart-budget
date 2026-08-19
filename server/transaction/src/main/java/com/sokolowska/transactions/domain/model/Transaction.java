package com.sokolowska.transactions.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
    Long id,
    String userId,
    LocalDateTime date,
    BigDecimal amount,
    String currency,
    String description,
    String category,
    String subcategory,
    String source,
    boolean recurring,
    boolean atmWithdrawal,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
