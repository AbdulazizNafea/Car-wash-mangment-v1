package com.example.finalproject.controller;

import com.example.finalproject.model.MyUser;
import com.example.finalproject.model.ServicesProduct;
import com.example.finalproject.service.ServicesProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ServicesProductController {

    private final ServicesProductService servicesProductService;


    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(servicesProductService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id,@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(servicesProductService.getById(id,myUser.getId()));
    }

//getByBranchId
@GetMapping("/getByBranchId/{branchId}")
public ResponseEntity getByBranchId(@PathVariable Integer branchId,@AuthenticationPrincipal MyUser myUser) {
    return ResponseEntity.status(HttpStatus.OK).body(servicesProductService.getByBranchId(branchId,myUser.getId()));
}

    @PutMapping("/merchant/update/{id}")
    public ResponseEntity update(@RequestBody @Valid ServicesProduct servicesProduct, @PathVariable Integer id, @AuthenticationPrincipal MyUser myUser) {
        servicesProductService.update(servicesProduct, id,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/merchant/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id,@AuthenticationPrincipal MyUser myUser) {
        servicesProductService.delete(id,myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    //////////////////////////////////
    //Assign here
    @PostMapping("/merchant/addServices/{branchId}")
    public ResponseEntity addServicesToBranch(@RequestBody @Valid ServicesProduct sp, @PathVariable Integer branchId,@AuthenticationPrincipal MyUser myUser) {
        servicesProductService.addServicesToBranch(sp,branchId, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }


}
