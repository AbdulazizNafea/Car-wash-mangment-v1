package com.example.finalproject;
import com.example.finalproject.model.Car;
import com.example.finalproject.model.Point;
import com.example.finalproject.repository.CarRepository;
import com.example.finalproject.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @InjectMocks
    CarService carService;

    @Mock
    CarRepository carRepository;

    Car Car1;

    @BeforeEach
    void setUp(){
        Car1 = new Car(null, "toyota", "suv", 2020);
    }

    @Test
    public void getCar() {
        carRepository.save(Car1);
        when(carRepository.findCarById(Car1.getId())).thenReturn(Car1);
        Car car = carRepository.findCarById(Car1.getId());
        //Note//read above note to understand
        Assertions.assertThat(Car1).isEqualTo(car);

        verify(carRepository, times(1)).findCarById(Car1.getId());
    }
}
