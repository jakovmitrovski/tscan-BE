package com.example.squick.services.impl;

import com.example.squick.models.Parking;
import com.example.squick.models.Ticket;
import com.example.squick.models.dtos.TicketDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.exceptions.CustomItemAlreadyExistsException;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.repositories.ParkingRepository;
import com.example.squick.repositories.TicketRepository;
import com.example.squick.services.TicketService;
import com.example.squick.utils.Constants;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ParkingRepository parkingRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public TicketServiceImpl(TicketRepository ticketRepository,
                             ParkingRepository parkingRepository) {
        this.ticketRepository = ticketRepository;
        this.parkingRepository = parkingRepository;
    }

    //TODO: Change return type
    @Override
    public Optional<Ticket> findTicketById(String identifier) {

        if (identifier.length() != Constants.ticketIdentifierLength)
            throw new BadRequestException(Constants.invalidTicketIdentifier);

        try {
            Long.parseLong(identifier);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.invalidTicketIdentifier);
        }

        Long parkingId = Long.parseLong(identifier.substring(0, Constants.ticketIdentifierParkingIdLength));
        Long ticketValue = Long.parseLong(identifier.substring(Constants.ticketIdentifierParkingIdLength, Constants.ticketIdentifierLength));

        parkingRepository.findById(parkingId).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));

        try {
            return ticketRepository.findByIdAndValue(parkingId, ticketValue);
        } catch (Exception exception) {
            throw new CustomNotFoundException(Constants.ticketDoesNotExist);
        }
    }

    @Override
    public Optional<Boolean> delete(Long id) {

        ticketRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.ticketDoesNotExist));

        try {
            ticketRepository.deleteById(id);
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }

    }

    @Override
    public Optional<Boolean> edit(TicketDto dto, Long id) {

        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.ticketDoesNotExist));
        Parking parking = ValidateTicket(dto);

        try {
            ticket.setValue(dto.getValue());
            ticket.setParking(parking);
            ticket.setEntered(LocalDateTime.parse(dto.getEntered(), formatter));
            ticket.setExited(LocalDateTime.parse(dto.getExited(), formatter));
            ticketRepository.save(ticket);
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }

    @Override
    public Optional<Boolean> create(TicketDto ticketDto) {

        Parking parking = ValidateTicket(ticketDto);

        Random generator = new Random();
        long ticketValue = generator.nextInt(999999);

        while (ticketRepository.findByParkingIdAndValue(ticketDto.getParkingId(), ticketValue).isPresent())
            ticketValue = generator.nextInt(999999);

        try {
            ticketRepository.save(new Ticket(parking, ticketValue, LocalDateTime.parse(ticketDto.getEntered(), formatter), LocalDateTime.parse(ticketDto.getExited(), formatter)));
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }

    //TODO: Refactor?
    private Parking ValidateTicket(TicketDto ticketDto) {

        Parking parking = parkingRepository.findById(ticketDto.getParkingId()).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));

        if (ticketRepository.findByParkingIdAndValue(ticketDto.getParkingId(), ticketDto.getValue()).isPresent())
            throw new CustomItemAlreadyExistsException(Constants.ticketAlreadyExists);

        if (LocalDateTime.parse(ticketDto.getExited(), formatter).isBefore(LocalDateTime.parse(ticketDto.getEntered(), formatter)))
            throw new BadRequestException(Constants.invalidTicketHours);

        return parking;
    }

}

