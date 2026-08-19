package com.sokolowska.transactions.service;

import com.sokolowska.transactions.domain.model.Transaction;
import com.sokolowska.transactions.domain.repository.TransactionsRepository;
import com.sokolowska.transactions.infra.TransactionEntity;
import com.sokolowska.transactions.infra.mapper.TransactionMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

  private final TransactionsRepository transactionsRepository;
  private final TransactionMapper transactionMapper;

  public TransactionsService(
      TransactionsRepository transactionsRepository, TransactionMapper transactionMapper) {
    this.transactionsRepository = transactionsRepository;
    this.transactionMapper = transactionMapper;
  }

  public List<Transaction> getAll() {
    return transactionMapper.toDomainList(transactionsRepository.findAll());
  }

  public Long addTransaction(Transaction transaction) {
    return transactionsRepository.save(transactionMapper.toEntity(transaction)).getId();
  }

  public Transaction getById(Long id) {
    TransactionEntity transactionEntity = transactionsRepository.findById(id).orElseThrow();
    return transactionMapper.toDomain(transactionEntity);
  }
}
