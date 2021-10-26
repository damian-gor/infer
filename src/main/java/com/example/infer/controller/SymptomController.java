package com.example.infer.controller;

import com.example.infer.dto.SymptomDTO;
import com.example.infer.service.SymptomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/symptoms")
public class SymptomController {

    private final SymptomService symptomService;

    public SymptomController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    @ApiOperation(value = "Viewing existing symptoms",
            response = SymptomDTO.class, responseContainer = "List")
    @GetMapping("/symptoms")
    public ResponseEntity<List<SymptomDTO>> getSymptoms() {
        return ResponseEntity.ok(symptomService.getSymptoms());
    }

    @ApiOperation(value = "Adding new symptom",
            response = SymptomDTO.class)
    @PostMapping("/symptoms")
    public ResponseEntity<SymptomDTO> addSymptom(@RequestBody SymptomDTO symptomDTO) {
        return ResponseEntity.ok(symptomService.addSymptom(symptomDTO));
    }
}
