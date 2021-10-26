package com.example.infer.enums;

import java.math.BigDecimal;

public enum Prevalence {
    RARE("RARE", new BigDecimal("0.0005")),
    MODERATE("MODERATE", new BigDecimal("0.005")),
    LIKELY("LIKELY", new BigDecimal("0.03"));

    private final String nazwa;
    private final BigDecimal prevalence;

    Prevalence(String nazwa, BigDecimal prevalence) {
        this.nazwa = nazwa;
        this.prevalence = prevalence;
    }

    public String getNazwa() {
        return nazwa;
    }

    public BigDecimal getPrevalence() {
        return prevalence;
    }

    public String getPrevalenceNameByDecimal(BigDecimal decimal) {
        if (decimal.compareTo(Prevalence.RARE.prevalence) < 0) {
            return Prevalence.RARE.nazwa;
        } else if (decimal.compareTo(Prevalence.MODERATE.prevalence) < 0) {
            return Prevalence.MODERATE.nazwa;
        } else return Prevalence.LIKELY.nazwa;
    }
}
