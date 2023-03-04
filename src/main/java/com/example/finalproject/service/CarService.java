package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.CarRepository;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * I will make Marcher know about all his customers cars, not urgent.
 */
@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final MyUserRepository myUserRepository;


    public List<Car> getAll(){
        return carRepository.findAll();
    }
    public List<Car> get(Integer auth){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        List<Car> car = carRepository.findAllCarByCustomerId(myUser.getCustomer().getId());
        if (car.isEmpty()) {
            throw new ApiException("Car not found");
        }
        return car;
    }

    public void add(Car car){
        carRepository.save(car);
    }

    public void update(Car car,Integer carId,Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
//        Car oldCar= carRepository.findCarByCustomerId(myUser.getCustomer().getId());
        Car oldCar =carRepository.findCarById(carId);
        if(oldCar == null){
            throw new ApiException("Car ID not found");
        }
        if(oldCar.getCustomer().getMyUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to update this Car!");
        }
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
    public void addCarToCustomer(Car car, Integer auth){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Customer customer =customerRepository.findCustomerById(myUser.getCustomer().getId());
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
