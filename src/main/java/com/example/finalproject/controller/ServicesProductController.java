package com.example.finalproject.controller;

import com.example.finalproject.model.Employee;
import com.example.finalproject.model.ServicesProduct;
import com.example.finalproject.service.ServicesProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(servicesProductService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid ServicesProduct servicesProduct) {
        servicesProductService.add(servicesProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid ServicesProduct servicesProduct, @PathVariable Integer id) {
        servicesProductService.update(servicesProduct, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        servicesProductService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    //////////////////////////////////
    //Assign here
    @PostMapping("/addServices/{branchId}")
    public ResponseEntity addServicesToBranch(@RequestBody @Valid ServicesProduct sp, @PathVariable Integer branchId) {
        servicesProductService.addServicesToBranch(sp,branchId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }


}
