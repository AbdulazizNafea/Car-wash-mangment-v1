package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Bill;
import com.example.finalproject.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServices {

    private final BillRepository billRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Bill> getAll(){
        return billRepository.findAll();
    }

    public Bill getById(Integer id){
        Bill bill = billRepository.findBillById(id);
        if (bill == null) {
            throw new ApiException("bill not found");
        }
        return bill;
    }

    public void add(Bill bill){
        bill.setCreatedDate(LocalDate.now());
        billRepository.save(bill);
    }

    public void update(Bill newBill,Integer id) {
        Bill bill= billRepository.findBillById(id);
        if(bill == null){
            throw new ApiException("bill ID not found");
        }
        bill.setTotalPrice(newBill.getTotalPrice());
        bill.setPaymentMethod(newBill.getPaymentMethod());
        bill.setTotalPoints(newBill.getTotalPoints());
        billRepository.save(bill);
    }

    public void delete(Integer id) {
        Bill bill= billRepository.findBillById(id);
        if(bill == null){
            throw new ApiException("Feature ID not found");
        }
        billRepository.delete(bill);
    }
    ///////////////////////////////////////////////////
    // assign here

}
