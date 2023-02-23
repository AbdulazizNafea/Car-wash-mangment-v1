package com.example.finalproject.repository;

import com.example.finalproject.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Feature,Integer> {
}
