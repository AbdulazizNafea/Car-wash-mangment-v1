package com.example.finalproject.repository;

import com.example.finalproject.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Integer> {

    public Bill findBillById(Integer id);
}
