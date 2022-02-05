package com.example.squick.services.impl;

import com.example.squick.models.Parking;
import com.example.squick.models.Ticket;
import com.example.squick.models.WorkingHours;
import com.example.squick.models.dtos.TicketDto;
import com.example.squick.models.dtos.TicketResponseDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.exceptions.CustomItemAlreadyExistsException;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.repositories.ParkingRepository;
import com.example.squick.repositories.TicketRepository;
import com.example.squick.services.TicketService;
import com.example.squick.utils.Constants;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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

    @Override
    public Optional<TicketResponseDto> scanTicket(String identifier) {

        try {
            Long.parseLong(identifier);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.invalidTicketIdentifier);
        }

        if (identifier.length() > Constants.ticketIdentifierLength)
            throw new BadRequestException(Constants.invalidTicketIdentifier);

        Long ticketValue = Long.parseLong(identifier.substring(identifier.length() - Constants.ticketIdentifierValueLength));
        Long parkingId = Long.parseLong(identifier.substring(0, identifier.length() - Constants.ticketIdentifierValueLength));

        Parking parking = parkingRepository.findById(parkingId).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));

        try {
            Ticket ticket = ticketRepository.findByParking_IdAndValue(parkingId, ticketValue).get();

            ticket.setExited(LocalDateTime.now());
            ticket = ticketRepository.save(ticket);

            TicketResponseDto response = new TicketResponseDto(ticket.getParking(), ticket.getEntered().format(formatter), ticket.getExited().format(formatter));
            double hoursParked = Math.ceil((Duration.between(ticket.getEntered(), ticket.getExited()).getSeconds()) / 3600.0);
            response.setPrice((long) (hoursParked * parking.getHourlyPrice()));
            return Optional.of(response);

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
    public Optional<TicketDto> create(TicketDto ticketDto) {

        Parking parking = ValidateTicket(ticketDto);

        LocalDateTime dateTimeEntered = LocalDateTime.parse(ticketDto.getEntered(), formatter);
        LocalDateTime dateTimeExited = null;

        if (ticketDto.getExited() != null && !ticketDto.getExited().isEmpty())
            dateTimeExited = LocalDateTime.parse(ticketDto.getExited(), formatter);

        Random generator = new Random();
        long ticketValue = generator.nextInt(999999);

        while (ticketRepository.findByParking_IdAndValue(ticketDto.getParkingId(), ticketValue).isPresent())
            ticketValue = generator.nextInt(999999);

        if (parking.getNumberOfFreeSpaces() == 0)
            throw new BadRequestException("Parking is currently full!");

        try {
            ticketRepository.save(new Ticket(parking, ticketValue, dateTimeEntered, dateTimeExited));
            parking.setNumberOfFreeSpaces(parking.getNumberOfFreeSpaces() - 1);
            parkingRepository.save(parking);

            ticketDto.setValue(ticketValue);
            return Optional.of(ticketDto);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }

    private Parking ValidateTicket(TicketDto ticketDto) {

        LocalDateTime entered = LocalDateTime.parse(ticketDto.getEntered(), formatter);

        Parking parking = parkingRepository.findById(ticketDto.getParkingId()).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));

        if (ticketDto.getValue() != null && ticketRepository.findByParking_IdAndValue(ticketDto.getParkingId(), ticketDto.getValue()).isPresent())
            throw new CustomItemAlreadyExistsException(Constants.ticketAlreadyExists);

        List<WorkingHours> workingHoursList = parking.getWorkingHours();
        WorkingHours workingHours = workingHoursList.stream().filter(x -> x.getDayOfWeek().name().equals(entered.getDayOfWeek().name().toUpperCase(Locale.ROOT))).findFirst()
                .orElseThrow(() -> new CustomNotFoundException(Constants.workHoursNotFound));

        if (entered.isBefore(LocalDateTime.of(LocalDateTime.now().toLocalDate(), workingHours.getTimeFrom()))
                || entered.isAfter(LocalDateTime.of(LocalDateTime.now().toLocalDate(), workingHours.getTimeTo())))
            throw new BadRequestException(Constants.badRequest);

        if (ticketDto.getExited() != null && !ticketDto.getExited().isEmpty() && LocalDateTime.parse(ticketDto.getExited(), formatter).isBefore(LocalDateTime.parse(ticketDto.getEntered(), formatter)))
            throw new BadRequestException(Constants.invalidTicketHours);

        return parking;
    }

}

