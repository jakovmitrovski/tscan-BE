package com.example.squick.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    public Ticket() {
    }

    public Ticket(Parking parking, Long value, LocalDateTime entered, LocalDateTime exited) {
        this.parking = parking;
        this.value = value;
        this.entered = entered;
        this.exited = exited;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Parking parking;

    Long value;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime entered;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime exited;
}
