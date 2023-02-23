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
    public List<MyUser> getAll(){
        return myUserRepository.findAll();
    }
    public MyUser getById(Integer id){
        MyUser myUser = myUserRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("User not found");
        }
        return myUser;
    }

    public void add(MyUser myUser){
        LocalDate date =  LocalDate.now();
        myUser.setCreatedAt(date);
        myUserRepository.save(myUser);
    }

    public void update(MyUser newMyUser,Integer id) {
        MyUser myUser= myUserRepository.findMyUserById(id);
        if(myUser == null){
            throw new ApiException("Merchant ID not found");
        }
        myUserRepository.save(myUser);
    }

    public void delete(Integer id) {
        MyUser myUser= myUserRepository.findMyUserById(id);
        if(myUser == null){
            throw new ApiException("Merchant ID not found");
        }
        myUserRepository.delete(myUser);
    }


    ///////////////////////////////////////////////////////////////////////
    //assign here

    public void assignMyUserToMerchant(MyUserDTO md){
        Merchant merchant = merchantRepository.findMerchantById(md.getId());
        if(merchant == null){
            throw new ApiException("merchant ID not found");
        }
        MyUser myUser = new MyUser(null,md.getUsername(),md.getPassword(),md.getEmail(),md.getPhone(),md.getCreatedAt(),md.getRole(),merchant,null);
        myUserRepository.save(myUser);
    }
    public void assignMyUserToCustomer(MyUserDTO md){
        Customer customer = customerRepository.findCustomerById(md.getId());
        if(customer == null){
            throw new ApiException("user ID not found");
        }
        MyUser myUser = new MyUser(null,md.getUsername(),md.getPassword(),md.getEmail(),md.getPhone(),md.getCreatedAt(),md.getRole(),null,customer);
        myUserRepository.save(myUser);
    }

}
