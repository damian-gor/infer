package com.example.infer.reposiotry;

import com.example.infer.model.SymptomProbability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomProbabilityRepository extends JpaRepository<SymptomProbability, String> {
    @Query("SELECT sp from SymptomProbability sp where sp.symptomProbabilityId.diseaseId = :diseaseId")
    List<SymptomProbability> findAllByDiseaseId(
            @Param("diseaseId") String diseaseId);
}
