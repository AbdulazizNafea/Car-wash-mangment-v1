package com.example.finalproject.controller;

import com.example.finalproject.DTO.MerchantDTO;
import com.example.finalproject.DTO.MyUserDTO;
import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.MerchantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.getById(id));
    }
    @GetMapping("/get")
    public ResponseEntity getById( @AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.getById(myUser.getId()));
    }


//    @PostMapping("/add")
//    public ResponseEntity add(@RequestBody @Valid Merchant merchant) {
//        merchantService.add(merchant);
//        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Merchant merchant, @PathVariable Integer id) {
        merchantService.update(merchant,id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        merchantService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
    //////////////////////////////////////
    //assign here




////////////////////////////////////////////////////////////////////////////////////
    //this is correct                                                             //
//    @PostMapping("/assignM")                                                   //
//    public ResponseEntity assignM(@RequestBody @Valid MyUserDTO md) {             //
//        merchantService.assignMyUserToMerchant(md);                               //
//        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");         //
//    }                                                                             //
///////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/assignU")
    public ResponseEntity assignU(@RequestBody @Valid MerchantDTO md,@AuthenticationPrincipal MyUser myUser) {
        merchantService.assignMyUserToMerchant2(md,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

}
