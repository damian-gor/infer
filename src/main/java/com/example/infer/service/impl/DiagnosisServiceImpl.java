package com.example.infer.service.impl;

import com.example.infer.dto.*;
import com.example.infer.enums.Sex;
import com.example.infer.service.DiagnosisService;
import com.example.infer.service.DiseaseService;
import com.example.infer.utils.CustomValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiseaseService diseaseService;
    private final CustomValidator customValidator;

    public DiagnosisServiceImpl(DiseaseService diseaseService,
                                CustomValidator customValidator) {
        this.diseaseService = diseaseService;
        this.customValidator = customValidator;
    }

    @Override
    public DiagnosisRespDTO getDiagnosis(DiagnosisReqDTO diagnosisReqDTO) {
        customValidator.validateRequest(diagnosisReqDTO);
        List<DiseaseDTO> diseases = diseaseService.findAllDiseasesBySex(Sex.valueOf(diagnosisReqDTO.getSex()));
        BigDecimal maxScore = BigDecimal.ZERO;
        DiseaseDTO mostProbableDisease = new DiseaseDTO();
        for (DiseaseDTO d : diseases) {
            BigDecimal tempScore = d.getOccurringSymptoms()
                    .stream()
                    .filter(dis -> diagnosisReqDTO.getReportedSymptoms()
                            .contains(dis.getSymptomId()))
                    .map(SymptomProbabilityDTO::getProbability)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (tempScore.compareTo(maxScore) > 0) {
                maxScore = tempScore;
                mostProbableDisease = d;
            }
        }
        return DiagnosisRespDTO.builder()
                .mostProbableDisease(MostProbableDiseaseDTO.builder()
                        .id(mostProbableDisease.getId())
                        .name(mostProbableDisease.getName())
                        .build())
                .build();
    }

}
