package com.example.finalproject.controller;

import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Car;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getAll());
    }

    @GetMapping("/getMyBranches")
    public ResponseEntity getById(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getMyBranches(myUser.getId()));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id,@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getById(id, myUser.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Branch branch,@AuthenticationPrincipal MyUser myUser) {
        branchService.add(branch,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Branch branch, @PathVariable Integer id,@AuthenticationPrincipal MyUser myUser) {
        branchService.update(branch, id,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id, @AuthenticationPrincipal MyUser myUser) {
        branchService.delete(id,myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
    /////////////////////////////////////////////////////
    //Assign here
    @PostMapping("/assignBranch")
    public ResponseEntity assignBranch(@RequestBody @Valid Branch branch, @AuthenticationPrincipal MyUser myUser) {
        branchService.addBranchToMerchant(branch,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
