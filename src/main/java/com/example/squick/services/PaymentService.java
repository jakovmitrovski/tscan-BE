package com.example.squick.services;

import com.example.squick.models.dtos.PaymentIntentDto;
import com.example.squick.models.dtos.CustomPaymentIntent;

import java.util.Optional;

public interface PaymentService {
    Optional<CustomPaymentIntent> createPaymentIntent(PaymentIntentDto paymentIntentDto);
}
