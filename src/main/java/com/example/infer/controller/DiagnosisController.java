package com.example.infer.controller;

import com.example.infer.dto.DiagnosisReqDTO;
import com.example.infer.dto.DiagnosisRespDTO;
import com.example.infer.service.DiagnosisService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }


    @ApiOperation(value = "Calculate most probable disease",
            response = DiagnosisRespDTO.class)
    @GetMapping("/diagnosis")
    public ResponseEntity<DiagnosisRespDTO> getDiagnosis(@RequestBody DiagnosisReqDTO diagnosisReqDTO) {
        return ResponseEntity.ok(diagnosisService.getDiagnosis(diagnosisReqDTO));
    }

}
