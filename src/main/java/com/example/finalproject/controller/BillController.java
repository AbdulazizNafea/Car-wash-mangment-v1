package com.example.finalproject.controller;

import com.example.finalproject.model.Bill;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.BillServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bill")
@RequiredArgsConstructor
public class BillController {

    private final BillServices billServices;

    //////////////////Test Admin\\\\\\\\\\\\\\\\\
    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(billServices.getAll());
    }


    //////////////////Customer\\\\\\\\\\\\\\\\\
    @GetMapping("/customer/getMyBills")
    public ResponseEntity getMyBillsForCustomer(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(billServices.getMyBillsCustomer(myUser.getId()));
    }

    @GetMapping("/customer/getMyBillById/{billId}")
    public ResponseEntity getBillByIdForCustomer(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer billId) {
        return ResponseEntity.status(HttpStatus.OK).body(billServices.getBillByIdForCustomer(myUser.getId(), billId));
    }


    //////////////////Merchanmt\\\\\\\\\\\\\\\\\
    @GetMapping("/merchant/getMyBills")
    public ResponseEntity getMyBillsForMerchant(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.OK).body(billServices.getMyBillsMerchant(myUser.getId()));
    }

    @GetMapping("/merchant/getMyBillById/{billId}")
    public ResponseEntity getBillByIdForMerchant(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer billId) {
        return ResponseEntity.status(HttpStatus.OK).body(billServices.getBillByIdForMerchant(myUser.getId(), billId));
    }

    @PostMapping("/merchant/add")
    public ResponseEntity add(@RequestBody @Valid Bill bill) {
        billServices.add(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/merchant/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Bill bill, @PathVariable Integer id) {
        billServices.update(bill, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/merchant/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        billServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    //======================================
    //Assign here
    @PostMapping("/merchant/addServicesToBill/{spId}/{billId}")
    public ResponseEntity addServicesToBill(@PathVariable Integer billId, @PathVariable Integer spId) {
        billServices.addServicesToBill(spId, billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/merchant/removeServicesFromBill/{spId}/{billId}")
    public ResponseEntity removeServicesFromBill(@PathVariable Integer billId, @PathVariable Integer spId) {
        billServices.removeServicesFromBill(spId, billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("removed");
    }

    @PostMapping("/merchant/addBillToCustomer/{billId}/customer/{customerId}")
    public ResponseEntity addBillToCustomer(@PathVariable Integer customerId, @PathVariable Integer billId, @AuthenticationPrincipal MyUser myUser) {
        billServices.addBillToCustomerAndMerchant(customerId, billId, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PostMapping("/merchant/addEmployToBill/{employeeId}/{billId}")
    public ResponseEntity addEmployToBill(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer billId, @PathVariable Integer employeeId) {
        billServices.addEmployToBill(employeeId, billId, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
