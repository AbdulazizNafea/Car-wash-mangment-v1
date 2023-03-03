package com.example.finalproject.controller;

import com.example.finalproject.DTO.MerchantDTO;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity get( @AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.get(myUser.getId()));
    }


//    @PostMapping("/add")
//    public ResponseEntity add(@RequestBody @Valid Merchant merchant) {
//        merchantService.add(merchant);
//        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
//    }

    @PutMapping("/merchant/update")
    public ResponseEntity update(@RequestBody @Valid Merchant merchant, @AuthenticationPrincipal MyUser myUser) {
        merchantService.update(merchant, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/merchant/delete")
    public ResponseEntity delete(@AuthenticationPrincipal MyUser myUser) {
        merchantService.delete(myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
    //////////////////////////////////////
    //assign here
    @PostMapping("/merchant/assignU")
    public ResponseEntity assignU(@RequestBody @Valid MerchantDTO md,@AuthenticationPrincipal MyUser myUser) {
        merchantService.assignMyUserToMerchant2(md,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    //registerEmployeeAsCashier
    @PostMapping("/merchant/registerEmployeeAsCashier/employee_Id{employeeId}")
    public ResponseEntity registerEmployeeAsCashier(@RequestBody @Valid MyUser newmyUser,@AuthenticationPrincipal MyUser myUser,@PathVariable Integer employeeId) {
        merchantService.registerEmployeeAsCashier(newmyUser,employeeId,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    //updateEmployCashier
    @PostMapping("/merchant/updateEmployCashier/employee_Id{employeeId}")
    public ResponseEntity updateEmployCashier(@RequestBody @Valid MyUser newmyUser,@AuthenticationPrincipal MyUser myUser,@PathVariable Integer employeeId) {
        merchantService.updateEmployCashier(newmyUser,employeeId,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("updated");
    }

    //deleteCashier
    @DeleteMapping("/merchant/deleteCashier/employee_Id{employeeId}")
    public ResponseEntity deleteCashier(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer employeeId) {
        merchantService.deleteCashier(myUser.getId(),employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body("deleted");
    }

}
