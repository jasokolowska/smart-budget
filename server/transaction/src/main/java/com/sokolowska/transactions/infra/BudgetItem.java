package com.sokolowska.transactions.infra;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "budget_items")
public class BudgetItem {

  @Id @UuidGenerator private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "budget_id", nullable = false)
  private Budget budget;

  @Column(name = "category_name", nullable = false)
  private String categoryName;

  @Column(name = "limit_amount", nullable = false, precision = 19, scale = 2)
  private BigDecimal limitAmount;

  @Column(name = "spent_amount", precision = 19, scale = 2)
  private BigDecimal spentAmount = BigDecimal.ZERO;

  public BudgetItem(Budget budget, String categoryName, BigDecimal limitAmount) {
    this.budget = budget;
    this.categoryName = categoryName;
    this.limitAmount = limitAmount;
  }
}
