package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Branch;
import com.example.finalproject.model.Car;
import com.example.finalproject.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    public List<Branch> getAll(){
        return branchRepository.findAll();
    }
    public Branch getById(Integer id){
        Branch branch = branchRepository.findBranchById(id);
        if (branch == null) {
            throw new ApiException("Branch not found");
        }
        return branch;
    }

    public void add(Branch branch){
        branchRepository.save(branch);
    }

    public void update(Branch branch,Integer id) {
        Branch oldBranchr= branchRepository.findBranchById(id);
        if(oldBranchr == null){
            throw new ApiException("Branch ID not found");
        }
//        oldEmp.setId(employee.getId());
        oldBranchr.setName(branch.getName());
        oldBranchr.setEmail(branch.getEmail());
        oldBranchr.setPhone(branch.getPhone());
        branchRepository.save(oldBranchr);
    }

    public void delete(Integer id) {
        Branch oldBranch= branchRepository.findBranchById(id);
        if(oldBranch == null){
            throw new ApiException("Branch ID not found");
        }
        branchRepository.delete(oldBranch);
    }
}
