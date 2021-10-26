package com.example.infer.service.impl;

import com.example.infer.dto.SymptomDTO;
import com.example.infer.enums.Sex;
import com.example.infer.model.Symptom;
import com.example.infer.reposiotry.SymptomRepository;
import com.example.infer.service.SymptomService;
import com.example.infer.utils.CustomMapper;
import com.example.infer.utils.CustomValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SymptomServiceImpl implements SymptomService {

    private final SymptomRepository symptomRepository;
    private final CustomMapper customMapper;
    private final CustomValidator customValidator;


    public SymptomServiceImpl(SymptomRepository symptomRepository,
                              CustomMapper customMapper,
                              CustomValidator customValidator) {
        this.symptomRepository = symptomRepository;
        this.customMapper = customMapper;
        this.customValidator = customValidator;
    }

    @Override
    public SymptomDTO addSymptom(SymptomDTO symptomDTO) {
        customValidator.validateRequest(symptomDTO);
        Symptom symptom = Symptom.builder()
                .name(symptomDTO.getName())
                .sex(Sex.valueOf(symptomDTO.getSex()))
                .build();
        symptom = symptomRepository.save(symptom);
        return customMapper.symptomToSymptomDTO(symptom);
    }

    @Override
    public List<SymptomDTO> getSymptoms() {
        return symptomRepository.findAll()
                .stream()
                .map(s -> customMapper.symptomToSymptomDTO(s))
                .collect(Collectors.toList());
    }

}
