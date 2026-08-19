package com.sokolowska.transactions.infra;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false, length = 100)
  private String userId;

  @Column(nullable = false)
  private LocalDateTime date;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 10)
  private String currency;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(length = 100)
  private String category;

  @Column(length = 100)
  private String subcategory;

  @Column(nullable = false, length = 50)
  //    @Enumerated(EnumType.STRING)
  private String source;

  @Column(nullable = false)
  private boolean recurring;

  @Column(name = "atm_withdrawal", nullable = false)
  private boolean atmWithdrawal;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
