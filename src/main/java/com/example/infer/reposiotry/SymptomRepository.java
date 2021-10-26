package com.example.infer.reposiotry;

import com.example.infer.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, String> {

    @Query("SELECT s.id from Symptom s where s.id in :ids")
    List<String> findIdsFromIdsList(@Param("ids") List<String> ids);
}
