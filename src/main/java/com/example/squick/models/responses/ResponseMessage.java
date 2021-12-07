package com.example.squick.models.responses;

import lombok.Data;

@Data
public class ResponseMessage {

    private final String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
