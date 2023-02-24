package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.CarRepository;
import com.example.finalproject.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;


    public List<Car> getAll(){
        return carRepository.findAll();
    }
    public Car getById(Integer id){
        Car car = carRepository.findCarById(id);
        if (car == null) {
            throw new ApiException("Car not found");
        }
        return car;
    }

    public void add(Car car){
        carRepository.save(car);
    }

    public void update(Car car,Integer id) {
        Car oldCar= carRepository.findCarById(id);
        if(oldCar == null){
            throw new ApiException("Car ID not found");
        }
//        oldEmp.setId(employee.getId());
        oldCar.setCarType(car.getCarType());
        oldCar.setBrand(car.getBrand());
        oldCar.setModel(car.getModel());
        carRepository.save(oldCar);
    }

    public void delete(Integer id) {
        Car oldCar= carRepository.findCarById(id);
        if(oldCar == null){
            throw new ApiException("Car ID not found");
        }
        carRepository.delete(oldCar);
    }

    ////////////////////////////////////////////////////////////
    //Assign here
    public void addCarToCustomer(Car car, Integer customerId){
        Customer customer =customerRepository.findCustomerById(customerId);
        if (customer == null ) {
            throw new ApiException("customer ID not found");
        }
        if(!customer.getMyUser().getRole().equalsIgnoreCase("customer")){
            throw new ApiException("Your role isn't customer");
        }
        car.setCustomer(customer);
        carRepository.save(car);
    }
}
