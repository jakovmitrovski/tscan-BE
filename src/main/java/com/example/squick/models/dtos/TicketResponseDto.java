package com.example.squick.models.dtos;

import com.example.squick.models.Parking;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TicketResponseDto {
    Parking parking;

    String entered;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String exited;

    Long price;

    public TicketResponseDto(Parking parking, String entered, String exited) {
        this.parking = parking;
        this.entered = entered;
        this.exited = exited;
    }
}
