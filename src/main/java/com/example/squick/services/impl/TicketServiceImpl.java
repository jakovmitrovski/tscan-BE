package com.example.squick.services.impl;

import com.example.squick.repositories.TicketRepository;
import com.example.squick.services.TicketService;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}
