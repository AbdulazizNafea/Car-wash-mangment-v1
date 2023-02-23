package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Rating;
import com.example.finalproject.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Rating> getAll(){
        return ratingRepository.findAll();
    }

    public Rating getById(Integer id){
        Rating rating = ratingRepository.findRatingById(id);
        if (rating == null) {
            throw new ApiException("rating not found");
        }
        return rating;
    }

    public void add(Rating rating){
        ratingRepository.save(rating);
    }

    public void update(Rating newRating,Integer id) {
        Rating rating= ratingRepository.findRatingById(id);
        if(rating == null){
            throw new ApiException("Rating ID not found");
        }
        rating.setRate(newRating.getRate());
        rating.setComment(newRating.getComment());
        ratingRepository.save(rating);
    }

    public void delete(Integer id) {
        Rating rating= ratingRepository.findRatingById(id);
        if(rating == null){
            throw new ApiException("Rating ID not found");
        }
        ratingRepository.delete(rating);
    }
    ///////////////////////////////////////////////////
    // assign here

}
