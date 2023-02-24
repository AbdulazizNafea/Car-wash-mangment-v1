package com.example.finalproject.repository;

import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Integer> {

    public Car findCarById(Integer id);
    public Car findCarByCustomerId(Integer customerId);
    List<Car> findAllCarByCustomerId(Integer customerId);
}
