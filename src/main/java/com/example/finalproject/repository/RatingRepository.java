package com.example.finalproject.repository;

import com.example.finalproject.model.Bill;
import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Employee;
import com.example.finalproject.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Integer> {

    public Rating findRatingById(Integer id);
    List<Rating> findAllByCreatedAtBetween(LocalDate parse, LocalDate parse1);

//    List<Employee> findAllByIdAndEmployeeAndCreatedAtBetween(LocalDate parse, LocalDate parse1);
//    List<Employee> findAllBy(LocalDate parse, LocalDate parse1);
}
