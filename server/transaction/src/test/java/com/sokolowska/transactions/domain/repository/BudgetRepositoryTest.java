package com.sokolowska.transactions.domain.repository;

import com.sokolowska.transactions.infra.Budget;
import com.sokolowska.transactions.infra.BudgetStatus;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {ModuleIntegrationTestConfig.class, TestcontainersConfiguration.class})
@ActiveProfiles("test2")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BudgetRepositoryTest {

  @Autowired private BudgetRepository budgetRepository;

  @Autowired private EntityManager entityManager;

  @Test
  @Transactional
  void detectNPlusOneProblem() {
    // given
    createBudget();
    createBudget();
    entityManager.flush();
    entityManager.clear();

    // when
    List<Budget> budgets = budgetRepository.findAll();

    // then
    budgets.forEach(
        budget -> {
          System.out.println("Budżet: " + budget.getId());
          budget
              .getItems()
              .forEach(
                  item ->
                      System.out.println(item.getCategoryName() + ": " + item.getLimitAmount()));
        });
  }

  private void createBudget() {
    Budget budget =
        Budget.builder()
            .userId(UUID.randomUUID())
            .period(YearMonth.now())
            .status(BudgetStatus.DRAFT)
            .build();

    budget.addItem("Food", new BigDecimal("200.00"));
    budget.addItem("Transport", new BigDecimal("100.00"));
    budget.addItem("Tax", new BigDecimal("112.00"));
    budget.addItem("Tax", new BigDecimal("112.00"));

    budgetRepository.save(budget);
  }
}
