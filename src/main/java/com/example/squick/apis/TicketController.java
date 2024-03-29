package com.example.squick.apis;

import com.example.squick.models.Ticket;
import com.example.squick.models.dtos.TicketDto;
import com.example.squick.models.dtos.TicketResponseDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.responses.ResponseMessage;
import com.example.squick.services.TicketService;
import com.example.squick.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{identifier}")
    ResponseEntity<TicketResponseDto> getTicket(@PathVariable String identifier) {

        return ticketService.scanTicket(identifier).map(ticket -> ResponseEntity.ok(ticket))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));
    }

    @PostMapping("/{parkingId}")
    ResponseEntity<TicketDto> createNewTicket(@Valid @PathVariable Long parkingId) {
        TicketDto newTicket = new TicketDto();
        newTicket.setEntered(LocalDateTime.now().format(formatter));
        newTicket.setParkingId(parkingId);
        return ticketService.create(newTicket).map(success -> ResponseEntity.ok(success))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseMessage> deleteTicket(@PathVariable Long id) {

        ResponseMessage message = new ResponseMessage(Constants.ticketDeletedSuccessfully);

        return ticketService.delete(id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{parkingId}")
    List<Ticket> findAllByParking(@PathVariable Long parkingId) {
        return ticketService.findAllByParking(parkingId);
    }

}
