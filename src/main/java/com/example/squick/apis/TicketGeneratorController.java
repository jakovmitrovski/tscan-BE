package com.example.squick.apis;

import com.example.squick.models.Parking;
import com.example.squick.models.dtos.TicketDto;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/ticket-generator")
public class TicketGeneratorController {

    private final ParkingController parkingController;
    private final TicketController ticketController;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public TicketGeneratorController(ParkingController parkingController,
                                     TicketController ticketController) {
        this.parkingController = parkingController;
        this.ticketController = ticketController;
    }

    @GetMapping
    public String getMainPage(Model model) {
        List<Parking> parkingList = parkingController.findAll();
        model.addAttribute("parkingList", parkingList);

        return "choose-parking";
    }

    @GetMapping("/step2/{parkingId}")
    public String getTicketGenerationPage(@PathVariable Long parkingId,
                                          Model model) {
        try {
            ExploreParkingDetailsProjection parking = parkingController.findParkingByIdExplore(parkingId).getBody();
            model.addAttribute("chosenParking", parking);
            model.addAttribute("parkingId", parkingId);

            return "generate-ticket";
        } catch (Exception exception) {
            return "redirect:/ticket-generator";
        }

    }

    @PostMapping("/generate/{parkingId}")
    public String generateTicket(@PathVariable long parkingId,
                                 Model model){

        TicketDto newTicket = new TicketDto();
        newTicket.setEntered(LocalDateTime.now().format(formatter));
        newTicket.setParkingId(parkingId);

        try{
            TicketDto generatedTicket = ticketController.createNewTicket(newTicket).getBody();

            StringBuilder ticketIdentifier = new StringBuilder();
            ticketIdentifier.append(parkingId);
            ticketIdentifier.append(generatedTicket.getValue());

            model.addAttribute("ticketParkingId", parkingId);
            model.addAttribute("ticketIdentifier", ticketIdentifier);
            model.addAttribute("notGenerated",false);
            return "result";
        }
        catch (Exception e){
            model.addAttribute("notGenerated",true);
            return "result";
        }

    }


}
