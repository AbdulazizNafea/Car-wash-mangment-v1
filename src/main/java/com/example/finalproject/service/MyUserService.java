package com.example.finalproject.service;

import com.example.finalproject.DTO.MyUserDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.MerchantRepository;
import com.example.finalproject.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MerchantRepository merchantRepository;
    private final MyUserRepository myUserRepository;
    private final CustomerRepository customerRepository;


    // crud here
    public List<MyUser> getAll() {
        return myUserRepository.findAll();
    }

    public MyUser getById(Integer id) {
        MyUser myUser = myUserRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("User not found");
        }
        return myUser;
    }

//    public void add(MyUser myUser){
//        LocalDate date =  LocalDate.now();
//        myUser.setCreatedAt(date);
//        myUserRepository.save(myUser);
//    }

    //register user
    public void register(MyUser user) {
//        user.setRole("USER");
        if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            throw new ApiException("Password should be not empty and more than 3");
        }
        LocalDate date = LocalDate.now();
        user.setCreatedAt(date);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        myUserRepository.save(user);
    }

    public void update(MyUser newMyUser, Integer id) {
        MyUser myUser = myUserRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("Merchant ID not found");
        }
        myUserRepository.save(myUser);
    }

    public void delete(Integer id) {
        MyUser myUser = myUserRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("Merchant ID not found");
        }
        myUserRepository.delete(myUser);
    }


    ///////////////////////////////////////////////////////////////////////
    //assign here


}
