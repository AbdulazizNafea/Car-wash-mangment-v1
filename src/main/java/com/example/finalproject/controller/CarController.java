package com.example.finalproject.controller;

import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Car;
import com.example.finalproject.model.Employee;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity get(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.get(myUser.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Car car) {
        carService.add(car);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Car car, @PathVariable Integer id, @AuthenticationPrincipal MyUser myUser) {
        carService.update(car, id ,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    /////////////////////////////////////////////////////
    //Assign here
    @PostMapping("/addCar")
    public ResponseEntity addCarToCustomer(@RequestBody @Valid Car car, @AuthenticationPrincipal MyUser myUser) {
        carService.addCarToCustomer(car,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
