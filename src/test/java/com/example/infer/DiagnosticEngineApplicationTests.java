package com.example.infer;

import com.example.infer.controller.DiagnosisController;
import com.example.infer.controller.DiseaseController;
import com.example.infer.controller.SymptomController;
import com.example.infer.dto.*;
import com.example.infer.enums.Sex;
import com.example.infer.exception.MissingRequestParameterException;
import com.example.infer.exception.SymptomNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class DiagnosticEngineApplicationTests {

    @Autowired
    SymptomController symptomController;
    @Autowired
    DiseaseController diseaseController;
    @Autowired
    DiagnosisController diagnosisController;

    @Test
    public void test001_SymptomController_addSymptom() {
        SymptomDTO symptomDTO1 = SymptomDTO.builder()
                .name("kaszel produktywny") // 0
                .sex(Sex.BOTH.getNazwa())
                .build();
        SymptomDTO symptomDTOresp = symptomController.addSymptom(symptomDTO1)
                .getBody();
        Assertions.assertNotNull(symptomDTOresp);
        Assertions.assertNotNull(symptomDTOresp.getId());
        Assertions.assertEquals(symptomDTOresp.getName(), symptomDTO1.getName());
        Assertions.assertEquals(symptomDTOresp.getSex(), symptomDTO1.getSex());

    }

    @Test
    public void test002_SymptomController_getSymptoms() {
        symptomController.addSymptom(SymptomDTO.builder()
                .name("znaczne zmęczenie, uogólnione pogorszenie samopoczucia") // 1
                .sex(Sex.BOTH.getNazwa())
                .build());
        symptomController.addSymptom(SymptomDTO.builder()
                .name("gorączka") // 2
                .sex(Sex.BOTH.getNazwa())
                .build());
        symptomController.addSymptom(SymptomDTO.builder()
                .name("uczucie duszności") // 3
                .sex(Sex.BOTH.getNazwa())
                .build());
        symptomController.addSymptom(SymptomDTO.builder()
                .name("nadmierne pragnienie") // 4
                .sex(Sex.BOTH.getNazwa())
                .build());
        symptomController.addSymptom(SymptomDTO.builder()
                .name("częstszy głód") // 5
                .sex(Sex.BOTH.getNazwa())
                .build());
        Assertions.assertNotNull(symptomController.getSymptoms());
        Assertions.assertEquals(6, Objects.requireNonNull(symptomController.getSymptoms()
                .getBody())
                .size());
    }

    @Test
    public void test003_DiseaseController_addDisease() {
        List<SymptomDTO> symptomDTOList = symptomController.getSymptoms()
                .getBody();
        List<SymptomProbabilityDTO> occurringSymptoms = new ArrayList<>();
        assert symptomDTOList != null;
        occurringSymptoms.add(SymptomProbabilityDTO.builder()
                .symptomId(symptomDTOList.get(0)
                        .getId())
                .probability(new BigDecimal("0.6"))
                .build());
        occurringSymptoms.add(SymptomProbabilityDTO.builder()
                .symptomId(symptomDTOList.get(1)
                        .getId())
                .probability(new BigDecimal("0.78"))
                .build());
        occurringSymptoms.add(SymptomProbabilityDTO.builder()
                .symptomId(symptomDTOList.get(3)
                        .getId())
                .probability(new BigDecimal("0.23"))
                .build());
        DiseaseDTO diseaseDTO = DiseaseDTO.builder()
                .name("Zapalenie płuc")
                .prevalence(new BigDecimal("0.006"))
                .sex(Sex.BOTH.getNazwa())
                .occurringSymptoms(occurringSymptoms)
                .build();
        diseaseDTO = diseaseController.addDisease(diseaseDTO)
                .getBody();
        Assertions.assertNotNull(diseaseDTO);
        Assertions.assertNotNull(diseaseDTO.getId());
        occurringSymptoms = new ArrayList<>();
        occurringSymptoms.add(SymptomProbabilityDTO.builder()
                .symptomId(symptomDTOList.get(1)
                        .getId())
                .probability(new BigDecimal("0.44"))
                .build());
        occurringSymptoms.add(SymptomProbabilityDTO.builder()
                .symptomId(symptomDTOList.get(4)
                        .getId())
                .probability(new BigDecimal("0.70"))
                .build());
        occurringSymptoms.add(SymptomProbabilityDTO.builder()
                .symptomId(symptomDTOList.get(5)
                        .getId())
                .probability(new BigDecimal("0.56"))
                .build());
        diseaseDTO = DiseaseDTO.builder()
                .name("Cukrzyca typu 2")
                .prevalence(new BigDecimal("0.009"))
                .sex(Sex.BOTH.getNazwa())
                .occurringSymptoms(occurringSymptoms)
                .build();
        diseaseDTO = diseaseController.addDisease(diseaseDTO)
                .getBody();
        Assertions.assertNotNull(diseaseDTO);
        Assertions.assertNotNull(diseaseDTO.getId());
    }

    @Test
    public void test004_DiseaseController_getDiseases() {
        List<DiseaseDTO> diseaseDTOList = diseaseController.getDiseases()
                .getBody();
        Assertions.assertNotNull(diseaseDTOList);
        Assertions.assertEquals(diseaseDTOList.size(), 2);
    }

    @Test
    public void test005_DiagnosisController_getDiagnose() {
        /**
         * Zapalenie płuc
         */
        List<String> diseasesIds = Objects.requireNonNull(symptomController.getSymptoms()
                .getBody())
                .stream()
                .filter(s -> s.getName()
                        .equals("kaszel produktywny") || s.getName()
                        .equals("znaczne zmęczenie, uogólnione pogorszenie samopoczucia"))
                .map(SymptomDTO::getId)
                .collect(Collectors.toList());
        DiagnosisRespDTO diagnosisRespDTO =
                diagnosisController.getDiagnosis(DiagnosisReqDTO.builder()
                        .sex("MALE")
                        .reportedSymptoms(diseasesIds)
                        .build())
                        .getBody();
        Assertions.assertNotNull(diagnosisRespDTO);
        Assertions.assertEquals("Zapalenie płuc", diagnosisRespDTO.getMostProbableDisease()
                .getName());
        /**
         * Cukrzyca
         */
        diseasesIds = Objects.requireNonNull(symptomController.getSymptoms()
                .getBody())
                .stream()
                .filter(s -> s.getName()
                        .equals("nadmierne pragnienie") || s.getName()
                        .equals("częstszy głód"))
                .map(SymptomDTO::getId)
                .collect(Collectors.toList());
        diagnosisRespDTO =
                diagnosisController.getDiagnosis(DiagnosisReqDTO.builder()
                        .sex("FEMALE")
                        .reportedSymptoms(diseasesIds)
                        .build())
                        .getBody();
        Assertions.assertNotNull(diagnosisRespDTO);
        Assertions.assertEquals("Cukrzyca typu 2", diagnosisRespDTO.getMostProbableDisease()
                .getName());


    }

    @Test
    public void test006_SymptomController_addSymptom_Exception() {
        SymptomDTO symptomDTO_noName = SymptomDTO.builder()
                .name(null)
                .sex(Sex.BOTH.getNazwa())
                .build();
        SymptomDTO symptomDTO_noSex = SymptomDTO.builder()
                .name("symptomDTO_noSex")
                .sex(null)
                .build();
        SymptomDTO symptomDTO_wrongSex = SymptomDTO.builder()
                .name("symptomDTO_wrongSex")
                .sex("test")
                .build();
        Assertions.assertThrows(MissingRequestParameterException.class,
                () -> symptomController.addSymptom(symptomDTO_noName));
        Assertions.assertThrows(MissingRequestParameterException.class,
                () -> symptomController.addSymptom(symptomDTO_noSex));
        Assertions.assertThrows(MissingRequestParameterException.class,
                () -> symptomController.addSymptom(symptomDTO_wrongSex));
    }

    @Test
    public void test007_DiseaseController_addDisease_Exception() {
        List<SymptomDTO> symptomDTOList = symptomController.getSymptoms()
                .getBody();
        List<SymptomProbabilityDTO> occurringSymptoms = new ArrayList<>();
        assert symptomDTOList != null;
        occurringSymptoms.add(SymptomProbabilityDTO.builder()
                .symptomId(symptomDTOList.get(0)
                        .getId())
                .probability(new BigDecimal("0.6"))
                .build());
        DiseaseDTO diseaseDTO = DiseaseDTO.builder()
                .name(null)
                .prevalence(new BigDecimal("0.006"))
                .sex(Sex.BOTH.getNazwa())
                .occurringSymptoms(occurringSymptoms)
                .build();
        Assertions.assertThrows(MissingRequestParameterException.class,
                () -> diseaseController.addDisease(diseaseDTO));
        diseaseDTO.setName("test");
        diseaseDTO.setPrevalence(null);
        Assertions.assertThrows(MissingRequestParameterException.class,
                () -> diseaseController.addDisease(diseaseDTO));
        diseaseDTO.setPrevalence(new BigDecimal("3.0"));
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> diseaseController.addDisease(diseaseDTO));
        diseaseDTO.setPrevalence(new BigDecimal("0.9"));
        diseaseDTO.getOccurringSymptoms()
                .get(0)
                .setProbability(new BigDecimal("-1.2"));
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> diseaseController.addDisease(diseaseDTO));
        diseaseDTO.setOccurringSymptoms(new ArrayList<>());
        Assertions.assertThrows(MissingRequestParameterException.class,
                () -> diseaseController.addDisease(diseaseDTO));
        diseaseDTO.setOccurringSymptoms(
                Collections.singletonList(SymptomProbabilityDTO.builder()
                        .probability(new BigDecimal("0.2"))
                        .symptomId("1234-brak-56")
                        .build()));
        Assertions.assertThrows(SymptomNotFoundException.class,
                () -> diseaseController.addDisease(diseaseDTO));
    }

    @Test
    public void test008_DiagnosisController_getDiagnosis_Exception() {
        List<String> diseasesIds = Objects.requireNonNull(symptomController.getSymptoms()
                .getBody())
                .stream()
                .limit(1)
                .map(SymptomDTO::getId)
                .collect(Collectors.toList());
        DiagnosisReqDTO diagnosisReqDTO =
                DiagnosisReqDTO.builder()
                        .sex("XYZ")
                        .reportedSymptoms(diseasesIds)
                        .build();
        Assertions.assertThrows(MissingRequestParameterException.class, () -> diagnosisController.getDiagnosis(diagnosisReqDTO));
        diagnosisReqDTO.setSex("FEMALE");
        diagnosisReqDTO.setReportedSymptoms(null);
        Assertions.assertThrows(MissingRequestParameterException.class, () -> diagnosisController.getDiagnosis(diagnosisReqDTO));
        diagnosisReqDTO.setReportedSymptoms(Collections.singletonList("test"));
        Assertions.assertThrows(SymptomNotFoundException.class, () -> diagnosisController.getDiagnosis(diagnosisReqDTO));
    }

}
