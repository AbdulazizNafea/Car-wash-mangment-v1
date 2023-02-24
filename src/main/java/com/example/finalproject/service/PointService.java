package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.Point;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.MerchantRepository;
import com.example.finalproject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final CustomerRepository customRepository;
    private final MerchantRepository merchantRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Point> getAll(){
        return pointRepository.findAll();
    }

    public Point getById(Integer id){
        Point point = pointRepository.findPointById(id);
        if (point == null) {
            throw new ApiException("point not found");
        }
        return point;
    }

    public void add(Point point){
        pointRepository.save(point);
    }

    public void update(Point newPoint,Integer id) {
        Point point= pointRepository.findPointById(id);
        if(point == null){
            throw new ApiException("Point ID not found");
        }
       point.setPoints(newPoint.getPoints());
        pointRepository.save(point);
    }

    public void delete(Integer id) {
        Point point= pointRepository.findPointById(id);
        if(point == null){
            throw new ApiException("Point ID not found");
        }
        pointRepository.delete(point);
    }
    ///////////////////////////////////////////////////
    // assign here

    public void assignPointToCustomerAndMerchant(Point newPoint, Integer customerId,Integer merchantId){
        Customer customer = customRepository.findCustomerById(customerId);
        Merchant merchant = merchantRepository.findMerchantById(merchantId);
        Point point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, merchantId);
        if(point != null){
            throw new ApiException("Already added");
        }

        if(merchant == null){
            throw new ApiException("Merchant not found");
        }

        if(customer == null){
            throw new ApiException("Customer not found");
        }
//        if(newPoint.getCustomer().getId() == customerId && newPoint.getMerchant().getId() == merchantId){
//            throw new ApiException("U cant add customer to same merchant");
//        }
        newPoint.setCustomer(customer);
        newPoint.setMerchant(merchant);
        newPoint.setPoints(0);
        pointRepository.save(newPoint);

    }


}
