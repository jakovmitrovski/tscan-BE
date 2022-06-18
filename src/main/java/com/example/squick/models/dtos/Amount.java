package com.example.squick.models.dtos;

import javax.validation.constraints.NotNull;

public class Amount {
    @NotNull(message = "Цената е задолжителна!")
    Integer amount;

    public Amount(Integer amount) {
        this.amount = amount;
    }

    public Amount() {
    }

    public Integer getAmount() {
        return amount;
    }
}
