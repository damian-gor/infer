package com.example.infer.service;

import com.example.infer.dto.SymptomDTO;

import java.util.List;

public interface SymptomService {
    SymptomDTO addSymptom(SymptomDTO SymptomDTO);

    List<SymptomDTO> getSymptoms();
}
