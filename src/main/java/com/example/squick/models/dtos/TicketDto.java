package com.example.squick.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketDto {

    @NotNull(message = "Паркингот е задолжителен!")
    Long parkingId;

    Long value;

    @NotNull(message = "Вредноста е задолжителна!")
    String entered;

    String exited;

    public TicketDto() {
    }
}
