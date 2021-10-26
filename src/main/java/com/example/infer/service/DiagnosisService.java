package com.example.infer.service;

import com.example.infer.dto.DiagnosisReqDTO;
import com.example.infer.dto.DiagnosisRespDTO;

public interface DiagnosisService {
    DiagnosisRespDTO getDiagnosis(DiagnosisReqDTO diagnosisReqDTO);
}
