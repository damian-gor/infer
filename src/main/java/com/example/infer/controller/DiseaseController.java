package com.example.infer.controller;

import com.example.infer.dto.DiseaseDTO;
import com.example.infer.service.DiseaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @ApiOperation(value = "Viewing existing diseases",
            response = DiseaseDTO.class, responseContainer = "List")
    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseDTO>> getDiseases() {
        return ResponseEntity.ok(diseaseService.getDiseases());
    }

    @ApiOperation(value = "Adding new disease",
            response = DiseaseDTO.class)
    @PostMapping("/diseases")
    public ResponseEntity<DiseaseDTO> addDisease(@Valid @RequestBody DiseaseDTO diseaseDTO) {
        return ResponseEntity.ok(diseaseService.addDisease(diseaseDTO));
    }
}
