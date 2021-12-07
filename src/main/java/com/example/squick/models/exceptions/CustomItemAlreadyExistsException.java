package com.example.squick.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CustomItemAlreadyExistsException extends RuntimeException {
    public CustomItemAlreadyExistsException(String message) {
        super(message);
    }
}
