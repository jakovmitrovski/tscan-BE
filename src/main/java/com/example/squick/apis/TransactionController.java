package com.example.squick.apis;

import com.example.squick.models.Transaction;
import com.example.squick.models.dtos.TransactionDto;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.models.responses.ResponseAggregator;
import com.example.squick.models.responses.ResponseMessage;
import com.example.squick.services.TransactionService;
import com.example.squick.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{userId}")
    Page<Transaction> findAllTransactions(@PathVariable String userId,
                                          @RequestParam int year,
                                          @RequestParam int month,
                                          @RequestParam(required = false, defaultValue = "0") Integer start,
                                          @RequestParam(required = false, defaultValue = "100") Integer items) {
        return transactionService.filterTransactionsForUser(userId, year, month, start, items);
    }

    @GetMapping("/{userId}/all")
    Page<Transaction> findAllForUser(@PathVariable String userId,
                                     @RequestParam(required = false, defaultValue = "0") Integer start,
                                     @RequestParam(required = false, defaultValue = "15") Integer items) {
        return transactionService.findAllTransactionsForUser(userId, start, items);
    }

    @GetMapping("/{userId}/sum")
    ResponseEntity<ResponseAggregator> findCostsForUser(@PathVariable String userId,
                                                        @RequestParam int year,
                                                        @RequestParam int month) {
        Long sum = transactionService.totalCostsForUserForMonth(userId, year, month);
        ResponseAggregator aggregator = new ResponseAggregator(sum == null ? 0 : sum);
        return ResponseEntity.ok(aggregator);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseMessage> deleteTransaction(@PathVariable Long id) {

        ResponseMessage message = new ResponseMessage(Constants.transactionDeletedSuccessfully);

        return transactionService.delete(id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<ResponseMessage> editTransaction(@PathVariable Long id,
                                                    @Valid @RequestBody TransactionDto dto) {

        ResponseMessage message = new ResponseMessage(Constants.editSuccessful);

        return transactionService.edit(dto, id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    ResponseEntity<ResponseMessage> createTransaction(@Valid @RequestBody TransactionDto dto) {

        ResponseMessage message = new ResponseMessage(Constants.transactionCreatedSuccessfully);

        return transactionService.save(dto).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));
    }
}
