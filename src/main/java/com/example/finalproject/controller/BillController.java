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

    //////////////////Test Admin\\\\\\\\\\\\
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

    //merchant
    @PostMapping("/merchant/add")
    public ResponseEntity add(@RequestBody @Valid Bill bill) {
        billServices.add(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    //cashier
    @PostMapping("/cashier/add")
    public ResponseEntity add2(@RequestBody @Valid Bill bill) {
        billServices.add(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/merchant/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Bill bill, @PathVariable Integer id,@AuthenticationPrincipal MyUser myUser) {
        billServices.update(bill, id,myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

//    @DeleteMapping("/merchant/delete/{id}")
//    public ResponseEntity delete(@PathVariable Integer id) {
//        billServices.delete(id);
//        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
//    }

    //======================================
    //Assign here

    //merchant
    @PostMapping("/merchant/addServicesToBill/product_id-{spId}/bill_id-{billId}/branch_id-{branchId}")
    public ResponseEntity addServicesToBill(@PathVariable Integer billId, @PathVariable Integer branchId, @PathVariable Integer spId, @AuthenticationPrincipal MyUser myUser) {
        billServices.addServicesToBill(spId, billId, branchId, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    //cashier
    @PostMapping("/cashier/addServicesToBill/product_id-{spId}/bill_id-{billId}/branch_id{branchId}")
    public ResponseEntity addServicesToBill2(@PathVariable Integer billId, @PathVariable Integer branchId, @PathVariable Integer spId, @AuthenticationPrincipal MyUser myUser) {
        billServices.addServicesToBill(spId, billId, branchId, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    //merchant
    @PutMapping("/merchant/removeServicesFromBill/{spId}/{billId}")
    public ResponseEntity removeServicesFromBill(@PathVariable Integer billId, @PathVariable Integer spId) {
        billServices.removeServicesFromBill(spId, billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("removed");
    }

    //cashier
    @PutMapping("/cashier/removeServicesFromBill/{spId}/{billId}")
    public ResponseEntity removeServicesFromBill2(@PathVariable Integer billId, @PathVariable Integer spId) {
        billServices.removeServicesFromBill(spId, billId);
        return ResponseEntity.status(HttpStatus.CREATED).body("removed");
    }

    //merchant
    @PostMapping("/merchant/addBillToCustomer/bill_id-{billId}/customer_id-{customerId}/employee_id-{employeeId}")
    public ResponseEntity addBillToCustomerAndMerchantAndEmployee(@PathVariable Integer customerId, @PathVariable Integer billId, @PathVariable Integer employeeId, @AuthenticationPrincipal MyUser myUser) {
        billServices.addBillToCustomerAndMerchantAndEmployee(customerId, billId, employeeId, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    //cashier
    @PostMapping("/cashier/addBillToCustomer/bill_id-{billId}/customer_id-{customerId}/employee_id-{employeeId}")
    public ResponseEntity addBillToCustomerAndMerchantAndEmployee2(@PathVariable Integer customerId, @PathVariable Integer billId, @PathVariable Integer employeeId, @AuthenticationPrincipal MyUser myUser) {
        billServices.addBillToCustomerAndMerchantAndEmployee(customerId, billId, employeeId, myUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @GetMapping("/merchant/getAllIncomeForMerchant")
    public ResponseEntity getAllIncomeForMerchant(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billServices.getAllIncomeForMerchant(myUser.getId()));
    }

    @GetMapping("/merchant/getAllIncomeInTimeRangeForMerchant/start_date_{start}/end_date_{end}")
    public ResponseEntity getAllIncomeInTimeRangeForMerchant(@PathVariable String start, @PathVariable String end,@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billServices.getAllIncomeInTimeRangeForMerchant(start, end,myUser.getId()));
    }

    @GetMapping("/merchant/getAllDailyIncomeForMerchant")
    public ResponseEntity getAllDailyIncomeForMerchant(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billServices.getAllDailyIncomeForMerchant(myUser.getId()));
    }

    @GetMapping("/merchant/getAllDailyBillForMerchant")
    public ResponseEntity getAllDailyBillForMerchant(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billServices.getAllDailyBillForMerchant(myUser.getId()));
    }

    //getAllIncomeForBranch
    @GetMapping("/merchant/getAllIncomeForBranch/{branchId}")
    public ResponseEntity getAllDailyBillForMerchant(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer branchId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billServices.getAllIncomeForBranch(branchId,myUser.getId()));
    }


}
