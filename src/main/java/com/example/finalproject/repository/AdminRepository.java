package com.example.finalproject.repository;

import com.example.finalproject.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    public Admin findAdminById(Integer id);
}
