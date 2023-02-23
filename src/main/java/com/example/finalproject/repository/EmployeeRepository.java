package com.example.finalproject.repository;

import com.example.finalproject.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    public Employee findEmployeeById(Integer id);
}
