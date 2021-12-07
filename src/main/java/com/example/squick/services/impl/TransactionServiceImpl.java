package com.example.squick.services.impl;

import com.example.squick.repositories.TransactionRepository;
import com.example.squick.services.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
