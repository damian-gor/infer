package com.example.infer.service.impl;

import com.example.infer.dto.DiseaseDTO;
import com.example.infer.enums.Sex;
import com.example.infer.model.Disease;
import com.example.infer.model.SymptomProbability;
import com.example.infer.reposiotry.DiseaseRepository;
import com.example.infer.reposiotry.SymptomProbabilityRepository;
import com.example.infer.service.DiseaseService;
import com.example.infer.utils.CustomMapper;
import com.example.infer.utils.CustomValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final SymptomProbabilityRepository symptomProbabilityRepository;
    private final CustomMapper customMapper;
    private final CustomValidator customValidator;


    public DiseaseServiceImpl(DiseaseRepository diseaseRepository,
                              SymptomProbabilityRepository symptomProbabilityRepository,
                              CustomMapper customMapper,
                              CustomValidator customValidator) {
        this.diseaseRepository = diseaseRepository;
        this.symptomProbabilityRepository = symptomProbabilityRepository;
        this.customMapper = customMapper;
        this.customValidator = customValidator;
    }

    @Override
    public DiseaseDTO addDisease(DiseaseDTO diseaseDTO) {
        customValidator.validateRequest(diseaseDTO);
        Disease disease = Disease.builder()
                .name(diseaseDTO.getName())
                .sex(Sex.valueOf(diseaseDTO.getSex()))
                .prevalence(diseaseDTO.getPrevalence())
                .build();
        disease = diseaseRepository.save(disease);
        diseaseDTO.setId(disease.getId());
        List<SymptomProbability> symptomProbabilityList = customMapper.getSymptomProbabilityListFromDiseaseDTO(diseaseDTO);
        symptomProbabilityList = symptomProbabilityRepository.saveAll(symptomProbabilityList);
        return customMapper.diseaseAndSymptomsToDiseaseDTO(disease, symptomProbabilityList);
    }


    @Override
    public List<DiseaseDTO> getDiseases() {
        List<Disease> diseaseList = diseaseRepository.findAll();
        return diseaseList.stream()
                .map(d -> customMapper.diseaseAndSymptomsToDiseaseDTO(d, symptomProbabilityRepository.findAllByDiseaseId(d.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<DiseaseDTO> findAllDiseasesBySex(Sex sex) {
        List<Disease> diseaseList = diseaseRepository.findAllBySex(sex);
        return diseaseList.stream()
                .map(d -> customMapper.diseaseAndSymptomsToDiseaseDTO(d, symptomProbabilityRepository.findAllByDiseaseId(d.getId())))
                .collect(Collectors.toList());
    }

}
