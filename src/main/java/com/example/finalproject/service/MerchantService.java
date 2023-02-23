//package com.example.finalproject.service;
//
//import com.example.finalproject.apiException.ApiException;
//import com.example.finalproject.model.Admin;
//import com.example.finalproject.model.Merchant;
//import com.example.finalproject.repository.AdminRepository;
//import com.example.finalproject.repository.MerchantRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//import java.util.SimpleTimeZone;
//
//@Service
//@RequiredArgsConstructor
//public class MerchantService {
//    private final MerchantRepository merchantRepository;
//    private final AdminRepository adminRepository;
//
//    public List<Merchant> getAll(){
//        return merchantRepository.findAll();
//    }
//    public Merchant getById(Integer id){
//        Merchant merchant = merchantRepository.findMerchantById(id);
//        if (merchant == null) {
//            throw new ApiException("User not found");
//        }
//        return merchant;
//    }
//
//    public void add(Merchant merchant){
//        LocalDate localDate = LocalDate.now();
//        merchant.setCreated(localDate);
//        merchantRepository.save(merchant);
//    }
//
//    public void update(Merchant newMerchant,Integer id) {
//        Merchant merchant= merchantRepository.findMerchantById(id);
//        if(merchant == null){
//            throw new ApiException("Merchant ID not found");
//        }
//        merchant.setName(newMerchant.getName());
//        LocalDate localDate = LocalDate.now();
//        merchant.setCreated(localDate);
//        merchant.setEmail(newMerchant.getEmail());
//        merchant.setPhone(newMerchant.getPhone());
//        merchantRepository.save(merchant);
//    }
//
//    public void delete(Integer id) {
//        Merchant merchant= merchantRepository.findMerchantById(id);
//        if(merchant == null){
//            throw new ApiException("Merchant ID not found");
//        }
//        merchantRepository.delete(merchant);
//    }
//}
