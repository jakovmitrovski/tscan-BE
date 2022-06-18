package com.example.squick.apis;

import com.example.squick.models.dtos.Amount;
import com.example.squick.models.dtos.CustomPaymentIntent;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.services.PaymentService;
import com.example.squick.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<CustomPaymentIntent> findParkingByIdExplore(@Valid @RequestBody Amount amount) {
        return this.paymentService.createPaymentIntent(amount).map(intent -> ResponseEntity.ok().body(intent))
                .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
    }

}
