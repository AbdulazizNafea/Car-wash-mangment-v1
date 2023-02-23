package com.example.finalproject.repository;

import com.example.finalproject.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Integer> {

    public Merchant findMerchantById(Integer id);
}
