package com.example.squick.services;

import com.example.squick.models.Transaction;
import com.example.squick.models.dtos.TransactionDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Page<Transaction> findAllTransactionsForUser(String userId, int start, int items);

    Page<Transaction> filterTransactionsForUser(String userId, int year, int month, int start, int items);

    Long totalCostsForUserForMonth(String userId, int year, int month);

    Optional<Boolean> delete(Long id);

    Optional<Boolean> save(TransactionDto dto);

    Optional<Boolean> edit(TransactionDto dto, Long id);
}
