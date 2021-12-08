package com.example.squick.models.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDto {
    Long parkingId;

    Long value;

    LocalDateTime entered;

    LocalDateTime exited;

    public TicketDto(Long parkingId, Long value, LocalDateTime entered, LocalDateTime exited) {
        this.parkingId = parkingId;
        this.value = value;
        this.entered = entered;
        this.exited = exited;
    }
}
