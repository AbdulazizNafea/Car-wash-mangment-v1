package com.example.finalproject.controller;

import com.example.finalproject.model.MyUser;
import com.example.finalproject.model.Point;
import com.example.finalproject.service.PointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;


    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.getById(id));
    }
    @GetMapping("/get")
    public ResponseEntity getById(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.getMyPoint(myUser.getId()));
    }

    @GetMapping("/get/customer_id={customerId}")
    public ResponseEntity getById(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.getPointByCustomerId(customerId,myUser.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Point point) {
        pointService.add(point);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Point point, @PathVariable Integer id) {
        pointService.update(point, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        pointService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    ///////////////////////////
    //ASSIGN HERE
    @PostMapping("/merchant/assignPointToCustomerAndMerchant/Customer/{customerId}")
    public ResponseEntity assignPointToCustomerAndMerchant(@RequestBody Point point,@PathVariable Integer customerId,@AuthenticationPrincipal MyUser myUser) {
        pointService.assignPointToCustomerAndMerchant(point,customerId,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
