package com.example.infer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiseaseDTO {
    private String id;
    private String name;
    @DecimalMin(value = "0.0", message = "Prevalence should not be less than 0")
    @DecimalMax(value = "1.0", message = "Prevalence should not be greater than 1")
    private BigDecimal prevalence;
    private String sex;
    @Valid
    private List<SymptomProbabilityDTO> occurringSymptoms;

}
