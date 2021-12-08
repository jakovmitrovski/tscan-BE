package com.example.squick.services;

import com.example.squick.models.Ticket;
import com.example.squick.models.dtos.TicketDto;

import java.util.Optional;

public interface TicketService {

    Optional<Ticket> findTicketById(String identifier);

    Optional<Boolean> delete(Long id);

    Optional<Boolean> edit(TicketDto ticket, Long id);

    Optional<Boolean> create(TicketDto ticketDto);

}
