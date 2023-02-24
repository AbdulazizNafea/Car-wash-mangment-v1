package com.example.finalproject.controller;

import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Car;
import com.example.finalproject.model.Employee;
import com.example.finalproject.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Car car) {
        carService.add(car);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Car car, @PathVariable Integer id) {
        carService.update(car, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    /////////////////////////////////////////////////////
    //Assign here
    @PostMapping("/addCar/{customerId}")
    public ResponseEntity addCarToCustomer(@RequestBody @Valid Car car, @PathVariable Integer customerId) {
        carService.addCarToCustomer(car,customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
