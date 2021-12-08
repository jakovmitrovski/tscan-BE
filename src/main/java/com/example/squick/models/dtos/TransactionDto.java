package com.example.squick.models.dtos;

import com.example.squick.models.Ticket;
import com.example.squick.models.enumerations.PaymentStatus;
import lombok.Data;


@Data
public class TransactionDto {
    String userId;

    Long ticketId;

    Integer price;

    PaymentStatus paymentStatus;

    public TransactionDto(String userId, Long ticketId, Integer price, PaymentStatus paymentStatus) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }
}