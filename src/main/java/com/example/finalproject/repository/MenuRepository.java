package com.example.finalproject.repository;

import com.example.finalproject.model.Employee;
import com.example.finalproject.model.ServicesProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<ServicesProduct,Integer> {

    public Employee findEmployeeById(Integer id);
}
