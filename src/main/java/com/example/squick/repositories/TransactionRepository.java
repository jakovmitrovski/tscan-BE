package com.example.squick.repositories;

import com.example.squick.models.Transaction;
import com.example.squick.models.enumerations.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(String userId);

    //Filter all successful transactions by month and year
    //TODO: Should return Page not List
    @Query(value = "select * from transactions t where  t.userId = :userId and t.paymentStatus = PaymentStatus.SUCCESSFUL and t.createdAt.getMonth()=:month and t.createdAt.getYear()=:year", nativeQuery = true)
    List<Transaction> filterTransactions(String userId, int year, int month);

    //Filter by UserId and Status (may not be needed)
    List<Transaction> findByUserIdAndPaymentStatus(String userId, PaymentStatus status);

    List<Transaction> findByUserIdAndPaymentStatusAndAndTicketId(String userId, PaymentStatus status, Long ticketId);
}
