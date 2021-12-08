package com.example.squick.repositories;

import com.example.squick.models.Transaction;
import com.example.squick.models.enumerations.PaymentStatus;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(String userId);

    //Filter all successful transactions by month and year
    //TODO: Should return Page not List
    @Query(value = "select * from transactions t where t.user_id=:userId and t.payment_status='SUCCESSFUL' and t.created_at >= CAST(:periodFrom as date ) and t.created_at <= CAST(:periodTo as date );", nativeQuery = true)
    List<Transaction> filterTransactions(String userId, String periodFrom, String periodTo);

    //Filter by UserId and Status (may not be needed)
    List<Transaction> findByUserIdAndPaymentStatus(String userId, PaymentStatus status);

    List<Transaction> findByUserIdAndPaymentStatusAndAndTicketId(String userId, PaymentStatus status, Long ticketId);
}
