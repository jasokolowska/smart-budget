package com.sokolowska.transactions.infra;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(
    name = "budgets",
    uniqueConstraints = {
      @UniqueConstraint(
          columnNames = {"user_id", "period"}) // Jeden budżet na miesiąc dla użytkownika
    })
public class Budget {

  @Id @UuidGenerator private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "period", nullable = false)
  private YearMonth period;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private BudgetStatus status = BudgetStatus.DRAFT;

  // Relacja One-to-Many do pozycji budżetowych (kategorii)
  @OneToMany(
      mappedBy = "budget",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<BudgetItem> items = new ArrayList<>();

  // Pola audytowe wymagane przez PRD
  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "last_modified_at")
  private Instant lastModifiedAt;

  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  public void approveProposal() {
    if (this.status != BudgetStatus.AI_PROPOSAL) {
      throw new IllegalStateException("Only AI proposals can be approved directly.");
    }
    this.status = BudgetStatus.ACTIVE;
  }

  public void addItem(String category, BigDecimal limit) {
    BudgetItem item = new BudgetItem(this, category, limit);
    if (this.items == null) this.items = new ArrayList<>();
    this.items.add(item);
  }
}
