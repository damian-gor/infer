package com.example.infer.enums;

public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE"),
    BOTH("BOTH");

    private final String nazwa;

    Sex(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }
}
