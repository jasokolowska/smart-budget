package com.sokolowska.transactions.domain.repository;

import com.sokolowska.transactions.infra.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {}
