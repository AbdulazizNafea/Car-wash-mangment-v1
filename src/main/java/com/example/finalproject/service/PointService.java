package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.model.Point;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.MerchantRepository;
import com.example.finalproject.repository.MyUserRepository;
import com.example.finalproject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final CustomerRepository customRepository;
    private final MerchantRepository merchantRepository;
    private final MyUserRepository myUserRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Point> getAll() {
        return pointRepository.findAll();
    }

    public Point getById(Integer id) {
        Point point = pointRepository.findPointById(id);
        if (point == null) {
            throw new ApiException("point not found");
        }
        return point;
    }

    // just this important
    public List<Point> getMyPoint(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        List<Point> points = new ArrayList<>();

        if (myUser.getRole().equalsIgnoreCase("Merchant")) {
            points = pointRepository.findAllPointByMerchantId(myUser.getMerchant().getId());
        } else if (myUser.getRole().equalsIgnoreCase("Customer")) {
            points = pointRepository.findAllPointByMerchantId(myUser.getCustomer().getId());
        }

        if (points.isEmpty()) {
            throw new ApiException("no points");
        }
        return points;
    }
//not used
    public void add(Point point) {
        pointRepository.save(point);
    }

    /////////////////////////////////////////////////////
    //not used
    public void update(Point newPoint, Integer id) {
        Point point = pointRepository.findPointById(id);
        if (point == null) {
            throw new ApiException("Point ID not found");
        }
        point.setPoints(newPoint.getPoints());
        pointRepository.save(point);
    }

    public void delete(Integer id) {
        Point point = pointRepository.findPointById(id);
        if (point == null) {
            throw new ApiException("Point ID not found");
        }
        pointRepository.delete(point);
    }
    ///////////////////////////////////////////////////
    // assign here

    public void assignPointToCustomerAndMerchant(Point newPoint, Integer customerId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());

        Customer customer = customRepository.findCustomerById(customerId);
        Point point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, myUser.getMerchant().getId());


        if (point != null) {
            throw new ApiException("Already added");
        } else if (merchant == null) {
            throw new ApiException("Merchant not found");
        } else if (customer == null) {
            throw new ApiException("Customer not found");
        }

        newPoint.setCustomer(customer);
        newPoint.setMerchant(merchant);
        newPoint.setPoints(0);
        pointRepository.save(newPoint);

    }
}
