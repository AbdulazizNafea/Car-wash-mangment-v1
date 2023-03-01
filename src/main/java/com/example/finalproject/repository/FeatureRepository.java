package com.example.finalproject.repository;

import com.example.finalproject.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature,Integer> {
    public Feature findFeatureById(Integer id);
    public Feature findFeatureByBranchId(Integer branchId);
    public List<Feature> findAllFeatureByBranchId(Integer branchId);

}

