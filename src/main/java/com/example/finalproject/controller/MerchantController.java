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

    @DeleteMapping("/merchant/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        merchantService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
    //////////////////////////////////////
    //assign here
    @PostMapping("/assignU")
    public ResponseEntity assignU(@RequestBody @Valid MerchantDTO md,@AuthenticationPrincipal MyUser myUser) {
        merchantService.assignMyUserToMerchant2(md,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

}
