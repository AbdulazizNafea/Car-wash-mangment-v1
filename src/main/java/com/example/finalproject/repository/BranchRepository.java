package com.example.finalproject.repository;

import com.example.finalproject.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {
    public Branch findBranchById(Integer id);

    //public Branch findBranchByRatingId(Integer id);
    public List<Branch> findAllBranchByMerchantId(Integer merchantId);
}
