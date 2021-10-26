package com.example.infer.reposiotry;

import com.example.infer.enums.Sex;
import com.example.infer.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, String> {

    @Query("SELECT d from Disease d where d.sex = :sex or d.sex = 'BOTH'")
    List<Disease> findAllBySex(@Param("sex") Sex sex);

}
