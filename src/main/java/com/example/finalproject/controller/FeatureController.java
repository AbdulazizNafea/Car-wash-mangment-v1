package com.example.finalproject.controller;

import com.example.finalproject.model.Feature;
import com.example.finalproject.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feature")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;


    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(featureService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(featureService.getById(id));
    }

//    @PostMapping("/add")
//    public ResponseEntity add(@RequestBody @Valid Feature feature) {
//        featureService.add(feature);
//        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Feature feature, @PathVariable Integer id) {
        featureService.update(feature, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        featureService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
    //////////////////////////////////
    //Assign here
    @PostMapping("/addFeature/{branchId}")
    public ResponseEntity addCustomer(@RequestBody @Valid Feature feature,@PathVariable Integer branchId) {
        featureService.assignFeatureToBranch(feature,branchId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
