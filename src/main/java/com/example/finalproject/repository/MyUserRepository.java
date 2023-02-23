package com.example.finalproject.repository;

import com.example.finalproject.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser,Integer> {

    public MyUser findMyUserById(Integer id);
}
