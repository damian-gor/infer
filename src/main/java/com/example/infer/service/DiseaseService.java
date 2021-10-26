package com.example.infer.service;

import com.example.infer.dto.DiseaseDTO;
import com.example.infer.enums.Sex;

import java.util.List;

public interface DiseaseService {
    DiseaseDTO addDisease(DiseaseDTO diseaseDTO);

    List<DiseaseDTO> getDiseases();

    List<DiseaseDTO> findAllDiseasesBySex(Sex sex);
}
