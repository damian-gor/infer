package com.example.infer.exception;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException(String details) {
        this.message = "Błędne zapytanie: " + details;
    }
}
