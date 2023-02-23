package com.example.finalproject.repository;

import com.example.finalproject.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarWashRepository extends JpaRepository<Bill,Integer> {
}
