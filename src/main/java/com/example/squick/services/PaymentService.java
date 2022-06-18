package com.example.squick.services;

import com.example.squick.models.dtos.Amount;
import com.example.squick.models.dtos.CustomPaymentIntent;

import java.util.Optional;

public interface PaymentService {
    Optional<CustomPaymentIntent> createPaymentIntent(Amount amount);
}
