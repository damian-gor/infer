package com.example.infer.utils;

import com.example.infer.dto.DiseaseDTO;
import com.example.infer.dto.SymptomDTO;
import com.example.infer.dto.SymptomProbabilityDTO;
import com.example.infer.model.Disease;
import com.example.infer.model.Symptom;
import com.example.infer.model.SymptomProbability;
import com.example.infer.model.SymptomProbabilityId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomMapper {

    public SymptomDTO symptomToSymptomDTO(Symptom symptom) {
        return SymptomDTO.builder()
                .id(symptom.getId())
                .name(symptom.getName())
                .sex(symptom.getSex()
                        .getNazwa())
                .build();
    }

    public DiseaseDTO diseaseAndSymptomsToDiseaseDTO(Disease disease, List<SymptomProbability> symptomProbabilityList) {
        return DiseaseDTO.builder()
                .id(disease.getId())
                .name(disease.getName())
                .sex(disease.getSex()
                        .getNazwa())
                .prevalence(disease.getPrevalence())
                .occurringSymptoms(
                        symptomProbabilityList.stream()
                                .map(sp ->
                                        SymptomProbabilityDTO.builder()
                                                .symptomId(sp.getSymptomProbabilityId()
                                                        .getSymptomId())
                                                .probability(sp.getProbability())
                                                .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();

    }

    public List<SymptomProbability> getSymptomProbabilityListFromDiseaseDTO(DiseaseDTO diseaseDTO) {
        List<SymptomProbabilityDTO> symptomProbabilityDTOList = diseaseDTO.getOccurringSymptoms();
        return symptomProbabilityDTOList.stream()
                .map(
                        spDTO ->
                                SymptomProbability.builder()
                                        .symptomProbabilityId(SymptomProbabilityId.builder()
                                                .diseaseId(diseaseDTO.getId())
                                                .symptomId(spDTO.getSymptomId())
                                                .build())
                                        .probability(spDTO.getProbability())
                                        .build()
                )
                .collect(Collectors.toList());
    }
}
