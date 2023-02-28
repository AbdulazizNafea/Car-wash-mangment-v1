package com.example.finalproject;

import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CarRepository carRepository;

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    PointRepository pointRepository;

    MyUser myUser;
    Merchant myMerchant;

    Employee employee;
    Point point;
    Car car;


    @BeforeEach
    void setUp() {
        myUser = new MyUser(1, "abdulaziz", "123", "aziz@email.com", "0595999590", LocalDate.now(), "Admin", null, null);
        myMerchant = new Merchant(3, "company", "34567", myUser, null, null, null);
        employee = new Employee(1, "Aziz", "0595999859", 3.9, null, null, null);
        car = new Car(1,"GMC","kia",1999,null);
        point= new Point(1,69.0,null,null);
    }

    @Test
    public void findMyUserByIdTest() {
        myUserRepository.save(myUser);
        MyUser myUser1 = myUserRepository.findMyUserById(myUser.getId());
        Assertions.assertThat(myUser1).isEqualTo(myUser);
    }

    @Test
    public void findMerchantById() {
        merchantRepository.save(myMerchant);
        Merchant merchant1 = merchantRepository.findMerchantById(myMerchant.getId());
        Assertions.assertThat(merchant1).isEqualTo(myMerchant);
    }

    @Test
    public void findEmployeeById() {
        employeeRepository.save(employee);
        Employee employee1 = employeeRepository.findEmployeeById(employee.getId());
        Assertions.assertThat(employee1).isEqualTo(employee);
    }

    @Test
    public void findCarById() {
        carRepository.save(car);
        Car car1 = carRepository.findCarById(car.getId());
        Assertions.assertThat(car1).isEqualTo(car);
    }

    //findPointById
    @Test
    public void findPointById() {
        pointRepository.save(point);
        Point point1 = pointRepository.findPointById(point.getId());
        Assertions.assertThat(point1).isEqualTo(point);
    }

}
