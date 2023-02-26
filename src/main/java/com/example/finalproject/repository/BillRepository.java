package com.example.finalproject.repository;

import com.example.finalproject.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    public Bill findBillById(Integer id);

    //    findAllCarByCustomerId
    public List<Bill> findAllBillByMerchantId(Integer id);

    public List<Bill> findAllBillByCustomerId(Integer id);

   // List<Bill> findAllByCreatedDateBetween2(LocalDate dateStart, LocalDate dateEnd);

    List<Bill> findAllByCreatedDateBetween(LocalDate parse, LocalDate parse1);
}
