package com.example.squick.services;

import com.example.squick.models.Ticket;
import com.example.squick.models.dtos.TicketDto;
import com.example.squick.models.dtos.TicketResponseDto;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Optional<TicketResponseDto> scanTicket(String identifier);

    Optional<Boolean> delete(Long id);

    Optional<TicketDto> create(TicketDto ticketDto);

    List<Ticket> findAllByParking(Long parkingId);

}
