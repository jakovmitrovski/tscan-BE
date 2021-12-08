package com.example.squick.repositories;

import com.example.squick.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByIdAndValue(Long id, Long value);

    Optional<Ticket> findByParkingIdAndValue(Long parkingId, Long value);
}
