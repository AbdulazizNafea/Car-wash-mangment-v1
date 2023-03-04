package com.example.finalproject.service;

import com.example.finalproject.DTO.RatingDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * create Rating employees in time range -> coming to you in 09-03-2023
 */

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;
    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final MyUserRepository myUserRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Rating getById(Integer id) {
        Rating rating = ratingRepository.findRatingById(id);
        if (rating == null) {
            throw new ApiException("rating not found");
        }
        return rating;
    }

    public void add(Rating rating) {
        ratingRepository.save(rating);
    }

    public void update(Rating newRating, Integer id) {
        Rating rating = ratingRepository.findRatingById(id);
        if (rating == null) {
            throw new ApiException("Rating ID not found");
        }
        rating.setRate(newRating.getRate());
        rating.setComment(newRating.getComment());
        ratingRepository.save(rating);
    }

    public void delete(Integer id) {
        Rating rating = ratingRepository.findRatingById(id);
        if (rating == null) {
            throw new ApiException("Rating ID not found");
        }
        ratingRepository.delete(rating);
    }

    ///////////////////////////////////////////////////
    // assign here

    public void addSRatingsToBranch(Rating rating, Integer branchId, Integer billId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Bill bill = billRepository.findBillById(billId);
        Branch branch = branchRepository.findBranchById(branchId);
        Customer customer = customerRepository.findCustomerById(myUser.getCustomer().getId());
        if (branch == null) {
            throw new ApiException("Branch ID not found");
        } else if (bill == null) {
            throw new ApiException("bill not found");
        } else if (!bill.getMerchant().getBranch().contains(branch)) {
            throw new ApiException("bill not assign to this branch");
        } else if (!customer.getBill().contains(bill)) {
            throw new ApiException("you don't have this bill");
        }
        branch.setAvgRate(getBranchRating(branchId));

        rating.setBranch(branch);
        ratingRepository.save(rating);
    }

    public void addRatingsToEmployee(Rating rating, Integer employeeId, Integer billId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Customer customer = customerRepository.findCustomerById(myUser.getCustomer().getId());
        Bill bill = billRepository.findBillById(billId);

        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            throw new ApiException("Employee ID not found");
        } else if (!customer.getBill().contains(bill)) {
            throw new ApiException("you're not authorize to add rating , bill not found");
        }
        employee.setAvgRate(getEmployeeRating(employeeId));
        rating.setEmployee(employee);
        ratingRepository.save(rating);
    }



    public void addRatingsToBill(RatingDTO rd, Integer auth, Integer billId) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Customer customer = customerRepository.findCustomerById(myUser.getCustomer().getId());
        Bill bill = billRepository.findBillById(billId);
        if (bill == null) {
            throw new ApiException("bill ID not found");
        } else if (!customer.getBill().contains(bill)) {
            throw new ApiException("cant add rate to bill tou dont own");
        }
        Rating rating = new Rating(null, rd.getRate(), rd.getComment(), rd.getCreatedAt(), null, null,bill);
        ratingRepository.save(rating);
    }


    ///////////////////A\\\\\\\\\\\\\\\\\\\\\\\\\
    public Double getBranchRating(Integer branchId) {
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null) {
            throw new ApiException("Branch not found");
        }
        double avgRate = 0.0;
        double sumRate = 0.0;
        if (branch.getRatings().size() > 0) {
            int count = branch.getRatings().size();
            for (Rating rate : branch.getRatings()) {
                sumRate = sumRate + rate.getRate();
            }
            avgRate = sumRate / count;
        }

        return avgRate;
    }

    public Double getBranchRatingInTimeRange(Integer branchId,String start, String end,Integer auth) {
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null) {
            throw new ApiException("Branch not found");
        }else if (branch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("you dont hava access");
        }
        double avgRate = 0.0;
        double sumRate = 0.0;
        if (branch.getRatings().size() > 0) {
            int count = branch.getRatings().size();
            for (Rating rate : branch.getRatings()) {
                sumRate = sumRate + rate.getRate();
            }
            avgRate = sumRate / count;
        }

        return avgRate;
    }

    public Double getEmployeeRating(Integer employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            throw new ApiException("Branch not found");
        }
        double avgRate = 0.0;
        double sumRate = 0.0;
        if (employee.getRatings().size() > 0) {
            int count = employee.getRatings().size();
            for (Rating rate : employee.getRatings()) {
                sumRate = sumRate + rate.getRate();
            }
            avgRate = sumRate / count;
        }

        return avgRate;
    }




}
