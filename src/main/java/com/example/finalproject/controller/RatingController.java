package com.example.finalproject.controller;

import com.example.finalproject.DTO.RatingDTO;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.model.Rating;
import com.example.finalproject.model.ServicesProduct;
import com.example.finalproject.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;


    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Rating rating) {
        ratingService.add(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Rating rating, @PathVariable Integer id) {
        ratingService.update(rating, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        ratingService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    //////////////////////////////////
    //Assign here
    @PostMapping("/customer/addRating/{branchId}/{biilId}")
    public ResponseEntity addSRatingsToBranch(@RequestBody @Valid Rating rating, @PathVariable Integer biilId, @PathVariable Integer branchId, @AuthenticationPrincipal MyUser myUser) {
        ratingService.addSRatingsToBranch(rating,branchId,biilId,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
    @PostMapping("/customer/addRatingsToEmployee/employye_id{employeeId}/bill_id/{billId}")
    public ResponseEntity addRatingsToEmployee(@RequestBody @Valid Rating rating, @PathVariable Integer employeeId,@PathVariable Integer billId, @AuthenticationPrincipal MyUser myUser) {
        ratingService.addRatingsToEmployee(rating,employeeId,billId,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PostMapping("/customer/addRatingsToBill/{billId}")
    public ResponseEntity addRatingsToBill(@RequestBody @Valid RatingDTO rd,@AuthenticationPrincipal MyUser myUser,@PathVariable Integer billId) {
        ratingService.addRatingsToBill(rd,myUser.getId(),billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

//    @GetMapping("/getEmpRatingFor/{empId}/start/{startDate}/end/{endDate}")
//    public ResponseEntity getEmpRatingInTineRange(@PathVariable String startDate,@PathVariable String endDate, @PathVariable Integer empId, @AuthenticationPrincipal MyUser user){
//        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getEmpRatingInTineRange(startDate, endDate,user.getId(), empId));
//    }
}
