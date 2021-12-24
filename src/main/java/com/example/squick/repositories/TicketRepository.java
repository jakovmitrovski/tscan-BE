package com.example.squick.repositories;

import com.example.squick.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByParking_IdAndValue(Long parkingId, Long value);

    List<Ticket> findAllByParking_IdAndExitedEquals(Long parkingId, LocalDateTime exited);
}
