package com.example.squick.services.impl;

import com.example.squick.models.Ticket;
import com.example.squick.models.Transaction;
import com.example.squick.models.dtos.TransactionDto;
import com.example.squick.models.enumerations.PaymentStatus;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.repositories.TicketRepository;
import com.example.squick.repositories.TransactionRepository;
import com.example.squick.services.TransactionService;
import com.example.squick.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TicketRepository ticketRepository) {
        this.transactionRepository = transactionRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Transaction> findAllTransactionsForUser(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    @Override
    public Page<Transaction> filterTransactionsForUser(String userId, int year, int month, int start, int items) {

        if (month < 0 || month > 12 || year < 2000)
            throw new BadRequestException(Constants.badRequest);

        LocalDate periodFrom = LocalDate.of(year, month, 1);
        LocalDate periodTo =
                (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                        ? LocalDate.of(year, month, 31)
                        : (month == 4 || month == 6 || month == 9 || month == 11) ? LocalDate.of(year, month, 30)
                        : (year % 4 == 0) ? LocalDate.of(year, month, 29) : LocalDate.of(year, month, 28);

        Pageable pageable = PageRequest.of(start, items);

        return transactionRepository.filterTransactions(userId, periodFrom.toString(), periodTo.toString(), pageable);
    }


    @Override
    public Optional<Boolean> delete(Long id) {

        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));

        try {
            this.transactionRepository.delete(transaction);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }

        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> save(TransactionDto dto) {

        List<Transaction> transactions = transactionRepository.findByUserIdAndPaymentStatusAndAndTicketId(dto.getUserId(),
                PaymentStatus.SUCCESSFUL, dto.getTicketId());

        Ticket ticket = ticketRepository.findById(dto.getTicketId()).orElseThrow(() -> new CustomNotFoundException(Constants.ticketDoesNotExist));

        if (transactions.size() > 0)
            throw new BadRequestException(Constants.transactionAlreadyPaid);
        else {
            try {
                if (dto.getPrice() < 0)
                    throw new BadRequestException(Constants.invalidTransactionPrice);

                Transaction transaction = new Transaction(dto.getUserId(), ticket, dto.getPrice(), dto.getPaymentStatus());
                this.transactionRepository.save(transaction);
                return Optional.of(true);

            } catch (Exception exception) {
                throw new BadRequestException(Constants.badRequest);
            }
        }
    }

    @Override
    public Optional<Boolean> edit(TransactionDto dto, Long id) {

        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.transactionNotFoundMessage));
        Ticket ticket = ticketRepository.findById(dto.getTicketId()).orElseThrow(() -> new CustomNotFoundException(Constants.ticketDoesNotExist));

        List<Transaction> transactions = transactionRepository.findByUserIdAndPaymentStatusAndAndTicketId(dto.getUserId(),
                PaymentStatus.SUCCESSFUL, ticket.getId());

        if (transactions.size() > 0)
            throw new BadRequestException(Constants.transactionAlreadyPaid);

        if (dto.getPrice() < 0)
            throw new BadRequestException(Constants.invalidTransactionPrice);

        try {
            transaction.setUserId(dto.getUserId());
            transaction.setPaymentStatus(dto.getPaymentStatus());
            transaction.setTicket(ticket);
            transaction.setPrice(dto.getPrice());
            transactionRepository.save(transaction);
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }
}
