package com.example.finalproject.controller;


import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class MyUserController {

    private final MyUserService myUserService;


    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.getAll());
    }

    @GetMapping("/getMyUser")
    public ResponseEntity getById(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(201).body(myUserService.getMyUser(myUser.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid MyUser myUser, @PathVariable Integer id) {
        myUserService.update(myUser, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        myUserService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    //////////////////////////////////////////////////////////////////
    //assign here

//    @PostMapping("/assignMerchant")
//    public ResponseEntity assignMerchant(@RequestBody MyUserDTO md){
//       myUserService.assignMyUserToMerchant(md);
//        return   ResponseEntity.status(200).body("Add Address Done");
//    }
//    @PutMapping("/assignCustomer")
//    public ResponseEntity assignCustomer(@RequestBody MyUserDTO md){
//        myUserService.assignMyUserToCustomer(md);
//        return   ResponseEntity.status(200).body("Add Address Done");
//    }

    ///////////////////////\
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid MyUser user){
        myUserService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered !");
    }
    @PostMapping("/login")
    public ResponseEntity login(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome back");
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(HttpStatus.OK).body("logout!");
    }

}
