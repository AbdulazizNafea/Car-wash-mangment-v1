package com.example.finalproject.repository;

import com.example.finalproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClintRepository extends JpaRepository<Customer,Integer> {
    public Customer findClintById(Integer id);
}
