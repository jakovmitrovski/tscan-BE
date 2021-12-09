package com.example.squick.apis;

import com.example.squick.models.Ticket;
import com.example.squick.models.dtos.TicketDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.responses.ResponseMessage;
import com.example.squick.services.TicketService;
import com.example.squick.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{identifier}")
    ResponseEntity<Ticket> getTicket(@PathVariable String identifier) {

        return ticketService.findTicketById(identifier).map(ticket -> ResponseEntity.ok(ticket))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));
    }

    @PostMapping
    ResponseEntity<ResponseMessage> createNewTicket(@Valid @RequestBody TicketDto ticketDto) {

        ResponseMessage message = new ResponseMessage(Constants.ticketCreatedSuccessfully);

        return ticketService.create(ticketDto).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseMessage> editTicket(@Valid @RequestBody TicketDto ticketDto,
                                               @PathVariable Long id) {

        ResponseMessage message = new ResponseMessage(Constants.editSuccessful);

        return ticketService.edit(ticketDto, id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));

    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseMessage> deleteTicket(@PathVariable Long id) {

        ResponseMessage message = new ResponseMessage(Constants.ticketDeletedSuccessfully);

        return ticketService.delete(id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));
    }

}
