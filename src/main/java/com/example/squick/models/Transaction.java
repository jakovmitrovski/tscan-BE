package com.example.squick.models;

import com.example.squick.models.enumerations.PaymentStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    public Transaction() {
    }

    public Transaction(String userId, Ticket ticket, Integer price, PaymentStatus paymentStatus) {
        this.userId = userId;
        this.ticket = ticket;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userId;

    @ManyToOne
    Ticket ticket;

    @CreationTimestamp
    LocalDateTime createdAt;

    Integer price;

    @Enumerated(value = EnumType.STRING)
    PaymentStatus paymentStatus;
}
