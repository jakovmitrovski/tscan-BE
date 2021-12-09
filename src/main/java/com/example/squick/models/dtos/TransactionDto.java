package com.example.squick.models.dtos;

import com.example.squick.models.enumerations.PaymentStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class TransactionDto {

    @NotNull(message = "Корисникот е задолжителен!")
    String userId;

    @NotNull(message = "Тикетот е задолжителен!")
    Long ticketId;

    @NotNull(message = "Цената е задолжителна!")
    Integer price;

    @NotNull(message = "Статусот на плаќањето е задолжителен!")
    PaymentStatus paymentStatus;

    public TransactionDto(String userId, Long ticketId, Integer price, PaymentStatus paymentStatus) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }
}
