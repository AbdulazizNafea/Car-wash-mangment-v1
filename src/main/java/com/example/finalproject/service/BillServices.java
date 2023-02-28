package com.example.finalproject.service;


import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServices {

    private final BillRepository billRepository;
    private final ServicesProductRepository servicesProductRepository;
    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;
    private final PointRepository pointRepository;
    private final MyUserRepository myUserRepository;

    private final EmployeeRepository employeeRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public List<Bill> getMyBillsCustomer(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        List<Bill> bill = billRepository.findAllBillByCustomerId(myUser.getCustomer().getId());
        if (bill == null) {
            throw new ApiException("bill not found");
        }
        return bill;
    }

    public Bill getBillByIdForCustomer(Integer auth, Integer billId) {
        Bill bill = billRepository.findBillById(billId);
        if (bill == null) {
            throw new ApiException("bill not Found");
        }
        if (bill.getCustomer().getId() != auth) {
            throw new ApiException("Sorry , You do not have the authority to get this Bill!");
        }
        return bill;

    }

    //////////////////////////////////////////////////////////
    public List<Bill> getMyBillsMerchant(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);

        List<Bill> bill = billRepository.findAllBillByMerchantId(myUser.getMerchant().getId());
        if (bill == null) {
            throw new ApiException("bill not found");
        }
        return bill;
    }

    public Bill getBillByIdForMerchant(Integer auth, Integer billId) {
        Bill bill = billRepository.findBillById(billId);
        if (bill == null) {
            throw new ApiException("bill not Found");
        }
        if (bill.getMerchant().getId() != auth) {
            throw new ApiException("Sorry , You do not have the authority to get this Bill!");
        }
        return bill;

    }

    public void add(Bill bill) {
        bill.setCreatedDate(LocalDate.now());
        billRepository.save(bill);
    }

    public void update(Bill newBill, Integer id) {
        Bill bill = billRepository.findBillById(id);
        if (bill == null) {
            throw new ApiException("bill ID not found");
        }
        bill.setTotalPrice(newBill.getTotalPrice());
        bill.setPaymentMethod(newBill.getPaymentMethod());
        bill.setTotalPoints(newBill.getTotalPoints());
        billRepository.save(bill);
    }

    public void delete(Integer id) {
        Bill bill = billRepository.findBillById(id);
        if (bill == null) {
            throw new ApiException("Feature ID not found");
        }
        billRepository.delete(bill);
    }
    ///////////////////////////////////////////////////
    // assign here

    //Merchant
    public void addServicesToBill(Integer serviceId, Integer billId) {
        Bill bill = billRepository.findBillById(billId);
        ServicesProduct sp = servicesProductRepository.findServicesProductById(serviceId);
        if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (sp == null) {
            throw new ApiException("Services Product ID not found");
        }
        for (ServicesProduct ss : bill.getServicesProducts()) {
            if (ss.getId() == serviceId) {
                throw new ApiException("Services Product already added");
            }
        }
        double totalPrice = bill.getTotalPrice();
        double totalPoints = bill.getTotalPoints();
        bill.setCreatedDate(LocalDate.now());
        bill.setTotalPrice(sp.getPrice() + totalPrice);
        bill.setTotalPoints(sp.getPoint() + totalPoints);
        sp.setBill(bill);
        servicesProductRepository.save(sp);
        billRepository.save(bill);
    }

    public void addEmployToBill(Integer employeeId, Integer billId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Bill bill = billRepository.findBillById(billId);
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (employee.getBranch().getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("not auth");
        } else if (!myUser.getMerchant().getBill().contains(bill)) {
            throw new ApiException("not your bill");
        }

        bill.setEmployee(employee);
        billRepository.save(bill);

    }


    //Merchant
    public void removeServicesFromBill(Integer serviceId, Integer billId) {
        Bill bill = billRepository.findBillById(billId);
        ServicesProduct sp = servicesProductRepository.findServicesProductById(serviceId);
        if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (sp == null) {
            throw new ApiException("Services Product ID not found");
        } else if (bill.getServicesProducts().isEmpty()) {
            throw new ApiException("there is no Services Product to delete it");
        }
        double totalPrice = bill.getTotalPrice();
        double totalPoints = bill.getTotalPoints();
        bill.setTotalPrice(totalPrice - sp.getPrice());
        bill.setTotalPoints(totalPoints - sp.getPoint());
        sp.setBill(null);
        billRepository.save(bill);
        servicesProductRepository.save(sp);

    }

    //Merchant
    public void addBillToCustomerAndMerchant(Integer customerId, Integer billId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        Customer customer = customerRepository.findCustomerById(customerId);
        Bill bill = billRepository.findBillById(billId);
        Point point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, merchant.getId());

        if (point == null) {
            throw new ApiException("can't add points, please create loyalty point to Customer");
        } else if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (customer == null) {
            throw new ApiException("Customer ID not found");
        } else if (merchant == null) {
            throw new ApiException("Merchant ID not found");
        }
        // Buy by Points
        int totalPointToBuy = 0;
        if (bill.getPaymentMethod().equalsIgnoreCase("point")) {
            for (ServicesProduct sp : bill.getServicesProducts()) {
                totalPointToBuy = sp.getTotalPoints() + totalPointToBuy;
            }
            if (point.getPoints() >= totalPointToBuy) {
                point.setPoints(point.getPoints() - totalPointToBuy);
            }else {
                throw new ApiException("Customer point balance not enough to buy this service, please pay by cash");
            }
        }else {
            point.setPoints(bill.getTotalPoints());
        }
        bill.setMerchant(merchant);
        bill.setCustomer(customer);
        billRepository.save(bill);
        pointRepository.save(point);
    }

    //

    public List<Bill> getBillByCreatedDateBetween(String start, String end) throws ParseException {
        //format string to date
        List<Bill> bills = billRepository.findAllByCreatedDateBetween(LocalDate.parse(start), LocalDate.parse(end));
        return bills;
    }
}
