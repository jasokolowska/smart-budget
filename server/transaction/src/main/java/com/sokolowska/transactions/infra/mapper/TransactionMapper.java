package com.sokolowska.transactions.infra.mapper;

import com.sokolowska.transactions.domain.model.Transaction;
import com.sokolowska.transactions.infra.TransactionEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
  Transaction toDomain(TransactionEntity entity);

  TransactionEntity toEntity(Transaction domain);

  List<Transaction> toDomainList(List<TransactionEntity> entity);

  List<TransactionEntity> toEntityList(List<Transaction> domain);
}
