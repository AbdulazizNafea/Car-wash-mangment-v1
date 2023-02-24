package com.example.finalproject.service;

import com.example.finalproject.DTO.RatingDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.BillRepository;
import com.example.finalproject.repository.BranchRepository;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;
    private final BillRepository billRepository;


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

    public void addSRatingsToBranch(Rating rating, Integer branchId){
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null ) {
            throw new ApiException("Branch ID not found");
        }
        rating.setBranch(branch);
        ratingRepository.save(rating);
    }

    public void addRatingsToEmployee(Rating rating, Integer employeeId){
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null ) {
            throw new ApiException("Employee ID not found");
        }
        rating.setEmployee(employee);
        ratingRepository.save(rating);
    }

    public void addRatingsToBill(RatingDTO rd){
         Bill bill = billRepository.findBillById(rd.getId());
         Rating rating1 = ratingRepository.findRatingById(rd.getId());
        if (bill == null ) {
            throw new ApiException("Employee ID not found");
        }
        Rating rating = new Rating(null,rd.getRate(),rd.getComment(),null,null,bill);
        ratingRepository.save(rating);
    }

}
