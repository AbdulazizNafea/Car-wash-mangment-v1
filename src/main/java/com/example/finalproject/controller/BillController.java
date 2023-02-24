package com.example.finalproject.controller;

import com.example.finalproject.model.Bill;
import com.example.finalproject.service.BillServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bill")
@RequiredArgsConstructor
public class BillController {

    private final BillServices billServices;


    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(billServices.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(billServices.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Bill bill) {
        billServices.add(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Bill bill, @PathVariable Integer id) {
        billServices.update(bill, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        billServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }
    //////////////////////////////////////
    //Assign here
    @PostMapping("/addServicesToBill/{spId}/{billId}")
    public ResponseEntity addServicesToBill(@PathVariable Integer billId ,@PathVariable Integer spId) {
        billServices.addServicesToBill(spId,billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
    @PutMapping("/removeServicesFromBill/{spId}/{billId}")
    public ResponseEntity removeServicesFromBill(@PathVariable Integer billId , @PathVariable Integer spId) {
        billServices.removeServicesFromBill(spId,billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("removed");
    }

    @PostMapping("/addBillToCustomer/{billId}/customer/{customerId}/merchant/{merchantId}")
    public ResponseEntity addBillToCustomer(@PathVariable Integer customerId,@PathVariable Integer billId, @PathVariable Integer merchantId) {
        billServices.addBillToCustomerAndMerchant(customerId,merchantId,billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
