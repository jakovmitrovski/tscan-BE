package com.example.squick.models.dtos;

public class CustomPaymentIntent {
    String clientSecret;

    public CustomPaymentIntent() {
    }

    public CustomPaymentIntent(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }


}
