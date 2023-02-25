package com.example.finalproject.repository;

import com.example.finalproject.model.Employee;
import com.example.finalproject.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    public Employee findEmployeeById(Integer id);
    public List<Employee> findAllEmployeeByBranchId(Integer branchId);

//    public List<Employee> findAllEmployeeByMerchant(Merchant merchant);

}
