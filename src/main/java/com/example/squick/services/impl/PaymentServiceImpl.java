package com.example.squick.services.impl;

import com.example.squick.models.dtos.PaymentIntentDto;
import com.example.squick.models.dtos.CustomPaymentIntent;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.secret.key}")
    private String apiKey;

    @Override
    public Optional<CustomPaymentIntent> createPaymentIntent(PaymentIntentDto paymentIntentDto) {
        Stripe.apiKey = apiKey;
        List<Object> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentIntentDto.getAmount());
        params.put("currency", "mkd");
        params.put("payment_method_types", paymentMethodTypes);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("ticket_id", paymentIntentDto.getTicketId());
        metadata.put("user_id", paymentIntentDto.getUserId());
        metadata.put("price", paymentIntentDto.getPrice());
        params.put("metadata", metadata);
        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return Optional.of(new CustomPaymentIntent(paymentIntent.getClientSecret()));
        }catch (StripeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
