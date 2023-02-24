package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServices {

    private final BillRepository billRepository;
    private final ServicesProductRepository servicesProductRepository;
    private final CustomerRepository customerRepository;
    private  final MerchantRepository merchantRepository;
    private final PointRepository pointRepository;


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

    public void addServicesToBill(Integer serviceId,Integer billId){
        Bill bill = billRepository.findBillById(billId);
        ServicesProduct sp = servicesProductRepository.findServicesProductById(serviceId);
        if (bill == null ) {
            throw new ApiException("Bill ID not found");
        } else if (sp == null ) {
            throw new ApiException("Services Product ID not found");
        }
        for(ServicesProduct ss: bill.getServicesProducts()){
            if(ss.getId() == serviceId){
                throw new ApiException("Services Product already added");
            }
        }
        double totalPrice = bill.getTotalPrice();
        double totalPoints = bill.getTotalPoints();
        bill.setCreatedDate(LocalDate.now());
        bill.setTotalPrice(sp.getPrice()+totalPrice);
        bill.setTotalPoints(sp.getPoint()+totalPoints);
        sp.setBill(bill);
        servicesProductRepository.save(sp);
        billRepository.save(bill);
    }

    public void removeServicesFromBill(Integer serviceId,Integer billId){
        Bill bill = billRepository.findBillById(billId);
        ServicesProduct sp = servicesProductRepository.findServicesProductById(serviceId);
        if (bill == null ) {
            throw new ApiException("Bill ID not found");
        } else if (sp == null ) {
            throw new ApiException("Services Product ID not found");
        }else if (bill.getServicesProducts().isEmpty()){
            throw new ApiException("there is no Services Product to delete it");
        }
        double totalPrice = bill.getTotalPrice();
        double totalPoints = bill.getTotalPoints();
        bill.setTotalPrice(totalPrice-sp.getPrice());
        bill.setTotalPoints(totalPoints-sp.getPoint());
        sp.setBill(null);
        billRepository.save(bill);
        servicesProductRepository.save(sp);

    }

    public void addBillToCustomerAndMerchant(Integer customerId, Integer merchantId,Integer billId){
        Bill bill = billRepository.findBillById(billId);
        Customer customer = customerRepository.findCustomerById(customerId);
        Merchant merchant = merchantRepository.findMerchantById(merchantId);
        Point point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, merchantId);
        if (point == null) {
            throw new ApiException("can't add points");
        }
        if(bill == null){
            throw new ApiException("Bill ID not found");
        } else if (customer == null ) {
            throw new ApiException("Customer ID not found");
        }else if (merchant == null) {
            throw new ApiException("Merchant ID not found");

        }
        point.setPoints(bill.getTotalPoints());
        bill.setMerchant(merchant);
        bill.setCustomer(customer);
        billRepository.save(bill);
        pointRepository.save(point);
    }
}
