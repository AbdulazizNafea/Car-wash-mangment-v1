package com.example.finalproject.service;

import com.example.finalproject.DTO.MerchantDTO;
import com.example.finalproject.DTO.MyUserDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.repository.BranchRepository;
import com.example.finalproject.repository.MyUserRepository;
import com.example.finalproject.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final MyUserRepository myUserRepository;
    private final BranchRepository branchRepository;

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
    public Merchant get(Integer auth){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("User not found");
        }
        return merchant;
    }

//    public void add(Merchant merchant){
//        merchantRepository.save(merchant);
//    }

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

    //this is correct work
//    public void assignMyUserToMerchant(MyUserDTO md){
//        Merchant merchant = merchantRepository.findMerchantById(md.getId());
//        if(merchant == null){
//            throw new ApiException("user ID not found");
//        }
//        MyUser myUser1= new MyUser(null, md.getUsername(),md.getPassword(),md.getEmail(),md.getPhone(), LocalDate.now(),md.getRole(),merchant);
//        myUserRepository.save(myUser1);
//    }

    ////////this is the correct practice in my opinion\\\\\\\\\\\\\\
    public void assignMyUserToMerchant2(MerchantDTO md, Integer auth){

        MyUser myUser = myUserRepository.findMyUserById(auth);
        if(!myUser.getRole().equalsIgnoreCase("Merchant")){
            throw new ApiException("This Page Only For Merchants!!!!");
        }
        if (myUser.getMerchant() != null){
            throw new ApiException("Merchants werk!!!!");
        }
        if(myUser == null){
            throw new ApiException("user ID not found");
        }
        if(!myUser.getRole().equalsIgnoreCase("Merchant")){
            throw new ApiException("your role not merchant");
        }
        Merchant myMerchant = new Merchant(null,md.getCompany_name(),md.getCommercial_record(),myUser,null,null,null);
        merchantRepository.save(myMerchant);
    }


    /*
    if point by id ->
    pointr .getCustomer == id c && merchant id == point.get merchant id
     */


}
