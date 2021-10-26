package com.example.infer.exception;

import lombok.Data;

import java.util.List;

@Data
public class SymptomNotFoundException extends RuntimeException {
    private String message;

    public SymptomNotFoundException(List<String> missingSymptomsIds) {
        this.message = "Nie znaleziono symptomów o IDs: " + missingSymptomsIds.toString();
    }
}
