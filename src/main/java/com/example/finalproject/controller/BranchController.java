package com.example.finalproject.controller;

import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Car;
import com.example.finalproject.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Branch branch) {
        branchService.add(branch);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Branch branch, @PathVariable Integer id) {
        branchService.update(branch, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        branchService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
}
