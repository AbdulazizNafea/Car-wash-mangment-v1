package com.example.finalproject.controller;

import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.DTO.MerchantDTO;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Employee;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity get( @AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.get(myUser.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Customer customer) {
        customerService.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Customer customer, @AuthenticationPrincipal MyUser myUser) {
        customerService.update(customer, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@AuthenticationPrincipal MyUser myUser) {
        customerService.delete(myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    /////////////////////
    //Assign here
    @PostMapping("/addCustomer")
    public ResponseEntity addCustomer(@RequestBody @Valid CustomerDTO cd, @AuthenticationPrincipal MyUser myUser) {
        customerService.assignCustomerToMyUser(cd, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }


}
