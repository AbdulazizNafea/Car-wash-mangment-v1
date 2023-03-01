package com.example.finalproject.repository;

import com.example.finalproject.model.Employee;
import com.example.finalproject.model.ServicesProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesProductRepository extends JpaRepository<ServicesProduct,Integer> {

    public ServicesProduct findServicesProductById(Integer id);
    public List<ServicesProduct> findAllServicesProductByBranchId(Integer id);
}