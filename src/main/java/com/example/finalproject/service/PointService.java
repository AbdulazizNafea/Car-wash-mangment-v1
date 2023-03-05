package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/*
 * challenge Start from here
 * role:
 * - write note everywhere about everything in your mind.
 * - any new endpoint or update I will start count timing to see my performance.
 * - No SMOKING never ever  🚭
 * - No Phone, close it.
 * - take 10 min brake every 1 hour
 * - if I break any of these rules, I will be a loser
 * - light in the sky ✨
 */

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final CustomerRepository customRepository;
    private final MerchantRepository merchantRepository;
    private final MyUserRepository myUserRepository;
    private final EmployeeRepository employeeRepository;


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

    public Point getPointByCustomerId(Integer customerId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Employee employee = employeeRepository.findEmployeeById(myUser.getEmployee().getId());
        if(employee == null) {
            throw new ApiException("Employee not found");
        }
        Point point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, employee.getMyUser().getMerchant().getId());
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
    // my challenge After Bootcamp finished to improve myself.
    //
    // Merchant And Cashier can create point subscribe for user.
    //Challenge 1- make merchant and cashier able to assign in same end point.
    //start time 4:25 am ....
    //end time 4:45 am -> lazy boy -_-

    public void assignPointToCustomerAndMerchant(Point newPoint, Integer customerId, Integer auth) {
        Employee employee;
        Merchant merchant;
        Point point;

        MyUser myUser = myUserRepository.findMyUserById(auth);
        Customer customer = customRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        if (myUser.getRole().equalsIgnoreCase("Cashier")) {
            employee = employeeRepository.findEmployeeById(myUser.getEmployee().getId());
            if (employee == null) {
                throw new ApiException("Employee not found");
            } else if (employee.getMyUser().getId() != auth) {
                throw new ApiException("sorry, dont have auth");
            }
            point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, employee.getBranch().getMerchant().getId());
            if (point != null) {
                throw new ApiException("point Already added");
            }
            newPoint.setMerchant(employee.getBranch().getMerchant());
            newPoint.setCustomer(customer);
            newPoint.setPoints(0);
        } else if (myUser.getRole().equalsIgnoreCase("Merchant")) {
            merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
            if (merchant == null) {
                throw new ApiException("Merchant not found");
            } else if (merchant.getMyUser().getId() != auth) {
                throw new ApiException("sorry, dont have auth");
            }
            point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, myUser.getMerchant().getId());
            if (point != null) {
                throw new ApiException("point Already added");
            }
            newPoint.setMerchant(merchant);
            newPoint.setCustomer(customer);
            newPoint.setPoints(0);
        }
        pointRepository.save(newPoint);
    }
}
