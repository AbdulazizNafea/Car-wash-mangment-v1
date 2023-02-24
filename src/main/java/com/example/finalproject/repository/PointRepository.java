package com.example.finalproject.repository;

import com.example.finalproject.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point,Integer> {

    public Point findPointById(Integer id);
    public Point findPointByCustomerIdAndMerchantId(Integer customerId,Integer merchantId );

}

