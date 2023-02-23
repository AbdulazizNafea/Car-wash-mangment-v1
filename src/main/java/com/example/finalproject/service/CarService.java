package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Car;
import com.example.finalproject.model.Employee;
import com.example.finalproject.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;

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
}
