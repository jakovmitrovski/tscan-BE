package com.example.squick.apis;

import com.example.squick.models.dtos.PaymentIntentDto;
import com.example.squick.models.dtos.CustomPaymentIntent;
import com.example.squick.models.dtos.TransactionDto;
import com.example.squick.models.enumerations.PaymentStatus;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.services.PaymentService;
import com.example.squick.services.TransactionService;
import com.example.squick.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/stripe")
public class PaymentController {

    @Value("${stripe.webhook.secret}")
    String webhookSecret;

    TransactionService transactionService;
    PaymentService paymentService;

    public PaymentController(TransactionService transactionService, PaymentService paymentService) {
        this.transactionService = transactionService;
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<CustomPaymentIntent> generatePaymentIntent(@Valid @RequestBody PaymentIntentDto paymentIntentDto) {
        return this.paymentService.createPaymentIntent(paymentIntentDto).map(intent -> ResponseEntity.ok().body(intent))
                .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
    }

    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    public ResponseEntity<String> webhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("Failed signature verification");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            throw new BadRequestException("Deserialization failed!");
        }

        String json = dataObjectDeserializer.getRawJson();

        String userId = new Gson().fromJson(json, JsonObject.class).getAsJsonObject("metadata").get("user_id").getAsString();
        Long ticketId = new Gson().fromJson(json, JsonObject.class).getAsJsonObject("metadata").get("ticket_id").getAsLong();
        Integer price = new Gson().fromJson(json, JsonObject.class).getAsJsonObject("metadata").get("price").getAsInt();

        TransactionDto dto;

        switch (event.getType()) {
            case "payment_intent.succeeded":
                dto = new TransactionDto(userId, ticketId, price, PaymentStatus.SUCCESSFUL);
                transactionService.save(dto);
                break;
            case "payment_intent.payment_failed":
                dto = new TransactionDto(userId, ticketId, price, PaymentStatus.UNSUCCESSFUL);
                transactionService.save(dto);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
