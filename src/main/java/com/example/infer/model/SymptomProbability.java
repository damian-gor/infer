package com.example.infer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SymptomProbability {

    @EmbeddedId
    private SymptomProbabilityId symptomProbabilityId;
    private BigDecimal probability;
}
