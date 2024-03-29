package com.example.squick.repositories;

import com.example.squick.models.Transaction;
import com.example.squick.models.enumerations.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUserId(String userId, Pageable pageable);

    @Query(value = "select * from transactions t where t.user_id=:userId and t.payment_status='SUCCESSFUL' and t.created_at >= CAST(:periodFrom as date ) and t.created_at <= CAST(:periodTo as date )", nativeQuery = true)
    Page<Transaction> filterTransactions(String userId, String periodFrom, String periodTo, Pageable pageable);

    @Query(value = "select SUM(t.price) from transactions t where t.user_id=:userId and t.payment_status='SUCCESSFUL' and t.created_at >= CAST(:periodFrom as date ) and t.created_at <= CAST(:periodTo as date )", nativeQuery = true)
    Long totalCostsForUserForMonth(String userId, String periodFrom, String periodTo);

    List<Transaction> findByPaymentStatusAndAndTicketId(PaymentStatus status, Long ticketId);

    @Query(value = "select * from transactions join tickets where transactions.ticket_id=tickets.id and tickets.parking_id=:parkingId", nativeQuery = true)
    List<Transaction> findAllTransactionsForParking(Long parkingId);
}
