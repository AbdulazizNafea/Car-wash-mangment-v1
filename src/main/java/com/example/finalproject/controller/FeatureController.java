package com.example.finalproject.controller;

import com.example.finalproject.model.Feature;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/getFeatureByBranchId/{id}")
    public ResponseEntity getFeatureByBranchId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(featureService.getFeatureByBranchId(id));
    }

//    @PostMapping("/add")
//    public ResponseEntity add(@RequestBody @Valid Feature feature) {
//        featureService.add(feature);
//        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
//    }

    @PutMapping("/merchant/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Feature feature, @PathVariable Integer id, @AuthenticationPrincipal MyUser myUser) {
        featureService.update(feature, id,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/merchant/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id, @AuthenticationPrincipal MyUser myUser) {
        featureService.delete(id,myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
    //////////////////////////////////
    //Assign here
    @PostMapping("/merchant/addFeature/{branchId}")
    public ResponseEntity addCustomer(@RequestBody @Valid Feature feature, @PathVariable Integer branchId, @AuthenticationPrincipal MyUser myUser) {
        featureService.assignFeatureToBranch(feature,branchId,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
