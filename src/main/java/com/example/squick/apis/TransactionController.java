package com.example.squick.apis;

import com.example.squick.models.Transaction;
import com.example.squick.models.dtos.TransactionDto;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.models.responses.ResponseMessage;
import com.example.squick.services.TransactionService;
import com.example.squick.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{userId}")
    List<Transaction> findAllTransactions(@PathVariable String userId,
                                          @RequestParam int year,
                                          @RequestParam int month) {
        return transactionService.filterTransactionsForUser(userId, year, month);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseMessage> deleteTransaction(@PathVariable Long id) {

        ResponseMessage message = new ResponseMessage(Constants.transactionDeletedSuccessfully);

        return transactionService.delete(id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));

    }

    @PutMapping("/edit/{id}")
    ResponseEntity<ResponseMessage> editTransaction(@PathVariable Long id,
                                                    @RequestBody TransactionDto dto) {

        ResponseMessage message = new ResponseMessage(Constants.editSuccessful);

        return transactionService.edit(dto, id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));
    }

    @PostMapping("/create")
    ResponseEntity<ResponseMessage> createTransaction(@RequestBody TransactionDto dto) {

        ResponseMessage message = new ResponseMessage(Constants.transactionCreatedSuccessfully);

        return transactionService.save(dto).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));
    }
}
