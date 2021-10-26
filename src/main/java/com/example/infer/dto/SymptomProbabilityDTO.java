package com.example.infer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SymptomProbabilityDTO {
    private String symptomId;
    @DecimalMin(value = "0.0", message = "Prevalence should not be less than 0")
    @DecimalMax(value = "1.0", message = "Prevalence should not be greater than 1")
    private BigDecimal probability;
}
