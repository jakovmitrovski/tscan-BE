package com.example.squick.models;

import com.example.squick.models.enumerations.PaymentStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
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
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime createdAt;

    Integer price;

    @Enumerated(value = EnumType.STRING)
    PaymentStatus paymentStatus;
}
