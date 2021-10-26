package com.example.infer.utils;

import com.example.infer.dto.DiagnosisReqDTO;
import com.example.infer.dto.DiseaseDTO;
import com.example.infer.dto.SymptomDTO;
import com.example.infer.dto.SymptomProbabilityDTO;
import com.example.infer.enums.Sex;
import com.example.infer.exception.MissingRequestParameterException;
import com.example.infer.exception.SymptomNotFoundException;
import com.example.infer.reposiotry.SymptomRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomValidator {

    private final SymptomRepository symptomRepository;

    public CustomValidator(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    public void validateRequest(DiagnosisReqDTO diagnosisReqDTO) {
        if (diagnosisReqDTO.getSex() == null || !Arrays.stream(Sex.values())
                .map(Sex::getNazwa)
                .collect(Collectors.toList())
                .contains(diagnosisReqDTO.getSex()
                        .toUpperCase())) {
            throw new MissingRequestParameterException("DiagnosisReqDTO.sex", "String (wartości: MALE, FEMALE, BOTH)");
        }
        if (diagnosisReqDTO.getReportedSymptoms() == null || diagnosisReqDTO.getReportedSymptoms()
                .isEmpty()) {
            throw new MissingRequestParameterException("DiagnosisReqDTO.reportedSymptoms", "String[]");
        }
        diagnosisReqDTO.setReportedSymptoms(diagnosisReqDTO.getReportedSymptoms()
                .stream()
                .distinct()
                .collect(Collectors.toList()));
        checkIfSympromsExistsInDb(diagnosisReqDTO.getReportedSymptoms());
    }

    public void validateRequest(DiseaseDTO diseaseDTO) {
        if (diseaseDTO.getName() == null || diseaseDTO.getName()
                .length() == 0) {
            throw new MissingRequestParameterException("DiseaseDTO.name", "String");
        }
        if (diseaseDTO.getPrevalence() == null) {
            throw new MissingRequestParameterException("DiseaseDTO.prevalence", "Decimal");
        }
        if (diseaseDTO.getSex() == null || !Arrays.stream(Sex.values())
                .map(Sex::getNazwa)
                .collect(Collectors.toList())
                .contains(diseaseDTO.getSex()
                        .toUpperCase())) {
            throw new MissingRequestParameterException("DiseaseDTO.sex", "String (wartości: MALE, FEMALE, BOTH)");
        }
        if (diseaseDTO.getOccurringSymptoms() == null || diseaseDTO.getOccurringSymptoms()
                .isEmpty()) {
            throw new MissingRequestParameterException("DiseaseDTO.occurringSymptoms", "SymptomProbabilityDTO[]");
        }
        /** removing duplicates */
        diseaseDTO.setOccurringSymptoms(diseaseDTO.getOccurringSymptoms()
                .stream()
                .distinct()
                .collect(Collectors.toList()));
        List<String> symptomsIds = diseaseDTO.getOccurringSymptoms()
                .stream()
                .map(SymptomProbabilityDTO::getSymptomId)
                .collect(Collectors.toList());
        checkIfSympromsExistsInDb(symptomsIds);

    }

    private void checkIfSympromsExistsInDb(List<String> symptomsIds) {
        List<String> idsFromDb = symptomRepository.findIdsFromIdsList(symptomsIds);
        if (symptomsIds.size() != idsFromDb.size()) {
            for (String id : idsFromDb) {
                symptomsIds.remove(id);
            }
            throw new SymptomNotFoundException(symptomsIds);
        }
    }

    public void validateRequest(SymptomDTO symptomDTO) {
        if (symptomDTO.getName() == null || symptomDTO.getName()
                .length() == 0) {
            throw new MissingRequestParameterException("SymptomDTO.name", "String");
        }
        if (symptomDTO.getSex() == null || !Arrays.stream(Sex.values())
                .map(Sex::getNazwa)
                .collect(Collectors.toList())
                .contains(symptomDTO.getSex()
                        .toUpperCase())) {
            throw new MissingRequestParameterException("SymptomDTO.sex", "String (wartości: MALE, FEMALE, BOTH)");
        }
    }
}
