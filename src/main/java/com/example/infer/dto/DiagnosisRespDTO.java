package com.example.infer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DiagnosisRespDTO {
    private MostProbableDiseaseDTO mostProbableDisease;
}
