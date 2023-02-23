package com.example.finalproject.service;

import com.example.finalproject.DTO.MerchantDTO;
import com.example.finalproject.DTO.MyUserDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.repository.MyUserRepository;
import com.example.finalproject.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final MyUserRepository myUserRepository;

    public List<Merchant> getAll(){
        return merchantRepository.findAll();
    }
    public Merchant getById(Integer id){
        Merchant merchant = merchantRepository.findMerchantById(id);
        if (merchant == null) {
            throw new ApiException("User not found");
        }
        return merchant;
    }

    public void add(Merchant merchant){
        merchantRepository.save(merchant);
    }

    public void update(Merchant newMerchant,Integer id) {
        Merchant merchant= merchantRepository.findMerchantById(id);
        if(merchant == null){
            throw new ApiException("Merchant ID not found");
        }
        merchant.setCommercial_record(newMerchant.getCommercial_record());
        merchant.setCompany_name(newMerchant.getCompany_name());
        merchantRepository.save(merchant);
    }

    public void delete(Integer id) {
        Merchant merchant= merchantRepository.findMerchantById(id);
        if(merchant == null){
            throw new ApiException("Merchant ID not found");
        }
        merchantRepository.delete(merchant);
    }
    ///////////////////////////////////////////////////////////////////////////
    //All assign

    //need review
    public void assignMyUserToCustomer(MerchantDTO md){
        MyUser myUser = myUserRepository.findMyUserById(md.getId());
        if(myUser == null){
            throw new ApiException("user ID not found");
        }
        Merchant merchant = new Merchant(null,md.getCompany_name(),md.getCommercial_record(),myUser,myUser.getMerchant().getPoint(),myUser.getMerchant().getBranch());
        merchantRepository.save(merchant);
    }

}
