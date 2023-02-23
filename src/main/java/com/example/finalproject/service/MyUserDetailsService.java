//package com.example.finalproject.service;
//
//import com.example.finalproject.model.MyUser;
//import com.example.finalproject.repository.MyUser_AuthRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MyUserDetailsService implements UserDetailsService {
//    private final MyUser_AuthRepository myUser_AuthRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        MyUser myUser = myUser_AuthRepository.findMyUserByUsername(username);
//        if (myUser == null) {
//            throw new UsernameNotFoundException("Your credential not correct");
//        }
//        return myUser;
//    }
//}
