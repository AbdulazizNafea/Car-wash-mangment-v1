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

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid MyUser myUser, @AuthenticationPrincipal Integer id) {
        myUserService.update( myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @PutMapping("/delete")
    public ResponseEntity delete(@AuthenticationPrincipal MyUser myUser) {
        myUserService.delete(myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    //////////////////////////////////////////////////////////////////
    //assign here

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
