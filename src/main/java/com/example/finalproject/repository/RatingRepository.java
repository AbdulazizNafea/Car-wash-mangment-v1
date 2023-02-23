package com.example.finalproject.repository;

import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,Integer> {

    public Rating findRatingById(Integer id);
}
