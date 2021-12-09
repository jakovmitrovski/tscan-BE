package com.example.squick.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketDto {

    @NotNull(message = "Паркингот е задолжителен!")
    Long parkingId;

    @NotNull(message = "Вредноста е задолжителна!")
    Long value;

    String entered;

    String exited;

    public TicketDto(Long parkingId, Long value, String entered, String exited) {
        this.parkingId = parkingId;
        this.value = value;
        this.entered = entered;
        this.exited = exited;
    }
}
