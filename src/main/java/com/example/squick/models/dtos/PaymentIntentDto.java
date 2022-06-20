package com.example.squick.models.dtos;

import javax.validation.constraints.NotNull;

public class PaymentIntentDto {
    @NotNull(message = "Цената е задолжителна!")
    Integer amount;

    @NotNull(message = "Корисникот е задолжителен!")
    String userId;

    @NotNull(message = "Тикетот е задолжителен!")
    Long ticketId;

    @NotNull(message = "Цената е задолжителна!")
    Integer price;

    public PaymentIntentDto(Integer amount, String userId, Long ticketId, Integer price) {
        this.amount = amount;
        this.userId = userId;
        this.ticketId = ticketId;
        this.price = price;
    }

    public PaymentIntentDto() {
    }

    public Integer getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Integer getPrice() {
        return price;
    }
}
