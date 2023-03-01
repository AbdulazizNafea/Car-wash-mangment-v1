package com.example.finalproject.service;

import com.example.finalproject.DTO.MerchantDTO;
import com.example.finalproject.apiException.ApiException;
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

    public List<Merchant> getAll() {
        return merchantRepository.findAll();
    }

    public Merchant get(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        if (myUser == null || myUser.getMerchant().getId() == null) {
            throw new ApiException("merchant not found");
        }
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("merchant not found");
        }
        return merchant;
    }

    public void update(Merchant newMerchant, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant ID not found");
        }
        merchant.setCommercial_record(newMerchant.getCommercial_record());
        merchant.setCompany_name(newMerchant.getCompany_name());
        merchantRepository.save(merchant);
    }

    public void delete(Integer id) {
        MyUser myUser = myUserRepository.findMyUserById(id);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant ID not found");
        }
        merchantRepository.delete(merchant);
    }
    ///////////////////////////////////////////////////////////////////////////
    //All assign

    public void assignMyUserToMerchant2(MerchantDTO md, Integer auth) {

        MyUser myUser = myUserRepository.findMyUserById(auth);

         if (myUser.getMerchant() != null) {
            throw new ApiException("Merchants Already Existing !");
        } else if (myUser == null) {
            throw new ApiException("user ID not found");
        }

        Merchant myMerchant = new Merchant(null, md.getCompany_name(), md.getCommercial_record(), myUser, null, null, null);
        merchantRepository.save(myMerchant);
        myUserRepository.save(myUser);
    }
}
