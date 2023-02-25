package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.repository.BranchRepository;
import com.example.finalproject.repository.MerchantRepository;
import com.example.finalproject.repository.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final MerchantRepository merchantRepository;
    private final MyUserRepository myUserRepository;


    public List<Branch> getAll(){
        return branchRepository.findAll();
    }
    public List<Branch> getMyBranches(Integer auth){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        List<Branch> branches = branchRepository.findAllBranchByMerchantId(myUser.getMerchant().getId());
        if (branches.isEmpty()){
            throw new ApiException("Branches not found");
        }
        return branches;
    }
    public Branch getById(Integer id,Integer auth){
        Branch branch = branchRepository.findBranchById(id);
        if (branch == null) {
            throw new ApiException("Branch not found");
        }else if(branch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority to see this Branch !");
        }
        return branch;
    }

    public void add(Branch branch,Integer auth){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        branchRepository.save(branch);
    }

    public void update(Branch branch,Integer id,Integer auth) {
        Branch oldBranch= branchRepository.findBranchById(id);
        if(oldBranch == null){
            throw new ApiException("Branch ID not found");
        }else if(oldBranch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority to update this Branch !");
        }
        oldBranch.setName(branch.getName());
        oldBranch.setEmail(branch.getEmail());
        oldBranch.setPhone(branch.getPhone());
        branchRepository.save(oldBranch);
    }

    public void delete(Integer id,Integer auth) {
        Branch oldBranch= branchRepository.findBranchById(id);
        if(oldBranch == null){
            throw new ApiException("Branch ID not found");
        }else if(oldBranch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority to delete this Branch !");
        }
        branchRepository.delete(oldBranch);
    }
    /////////////////////////////////////////////////
    //assign here

    public void addBranchToMerchant(Branch branch, Integer auth){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null ) {
            throw new ApiException("merchant ID not found");
        }
        branch.setMerchant(merchant);
        branch.setCreated(LocalDate.now());
        branchRepository.save(branch);
    }



}
