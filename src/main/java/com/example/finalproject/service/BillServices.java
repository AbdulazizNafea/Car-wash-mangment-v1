package com.example.finalproject.service;


import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BillServices {

    private final BillRepository billRepository;
    private final ServicesProductRepository servicesProductRepository;
    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;
    private final PointRepository pointRepository;
    private final MyUserRepository myUserRepository;
    private final BranchRepository branchRepository;
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
    public void addServicesToBill(Integer serviceId, Integer billId, Integer branchId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Branch branch = branchRepository.findBranchById(branchId);
        Bill bill = billRepository.findBillById(billId);
        ServicesProduct sp = servicesProductRepository.findServicesProductById(serviceId);

        if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (sp == null) {
            throw new ApiException("Services Product ID not found");
        } else if (sp.getBranch().getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("Sorry , You do not have the Authority !");
        } else if (!branch.getServicesProducts().contains(sp)) {
            throw new ApiException("Sorry , Service not found in this Branch: " + branch.getName());
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
    public void addBillToCustomerAndMerchantAndEmployee(Integer customerId, Integer billId, Integer employeeId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        Customer customer = customerRepository.findCustomerById(customerId);
        Bill bill = billRepository.findBillById(billId);
        Point point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, merchant.getId());
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if (point == null) {
            throw new ApiException("can't add points, please create loyalty point to Customer");
        } else if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (customer == null) {
            throw new ApiException("Customer ID not found");
        } else if (merchant == null) {
            throw new ApiException("Merchant ID not found");
        } else if (employee == null) {
            throw new ApiException("Employee ID not found");
        } else if (employee.getBranch().getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("not auth");
        }
        // Buy by Points
        int totalPointToBuy = 0;
        if (bill.getPaymentMethod().equalsIgnoreCase("point")) {
            for (ServicesProduct sp : bill.getServicesProducts()) {
                totalPointToBuy = sp.getTotalPoints() + totalPointToBuy;
            }

            if (point.getPoints() >= totalPointPrice) {
                point.setPoints(point.getPoints() - totalPointPrice);
            } else {
                throw new ApiException("Customer point balance not enough to buy this bill");

            }
        } else {
            point.setPoints(bill.getTotalPoints());
        }
        bill.setMerchant(merchant);
        bill.setCustomer(customer);
        bill.setEmployee(employee);
        billRepository.save(bill);
        pointRepository.save(point);
    }

    //

    public List<Bill> getBillByCreatedDateBetween(String start, String end) throws ParseException {
        //format string to date
        List<Bill> bills = billRepository.findAllByCreatedDateBetween(LocalDate.parse(start), LocalDate.parse(end));
        return bills;
    }


    public String getAllIncomeForMerchant(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant not found");
        }
        double totalIncome = 0.0;
        for (Bill bill : merchant.getBill()) {
            totalIncome = totalIncome + bill.getTotalPrice();
        }
        return "#Bill count is: " + merchant.getBill().size() + "\t#Total Income is: " + totalIncome;
    }

    public String getAllIncomeInTimeRangeForMerchant(String start, String end, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant not found");
        }
        double totalIncome = 0.0;
        int count = 0;
        List<Bill> bills = billRepository.findAllByCreatedDateBetween(LocalDate.parse(start), LocalDate.parse(end));
        for (Bill i : bills) {
            if (i.getMerchant().getMyUser().getId() == auth) {
                totalIncome = totalIncome + i.getTotalPrice();
                count++;
            }
        }
        return "#Bill count is: " + count + "\t#Total Income is: " + totalIncome;
    }

    public String getAllIncomeForBranch(Integer branchId, Integer auth) {
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null) {
            throw new ApiException("Branch not found");
        } else if (branch.getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("Sorry , You do not have the Authority !");
        }

        List<Employee> employees = employeeRepository.findAllEmployeeByBranchId(branchId);
        double totalIncome = 0.0;
        int countBill = employees.size();

        for (Employee e : branch.getEmployees()) {
            if (!e.getBill().isEmpty()) {
                for (Bill bill : e.getBill()) {
                    totalIncome = totalIncome + bill.getTotalPrice();
                }
            }
        }
        return "#All Brnch income is: " + totalIncome + " \t#Number of bill is: " + countBill;
    }

    public List<String[]> getAllDailyIncomeForMerchant(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant not found");
        }

        Map<LocalDate, Double> map = new HashMap<>();
        merchant.getBill().forEach((i)->{
            if(map.containsKey(i.getCreatedDate())){
                double oldValue=map.get(i.getCreatedDate());
                map.replace(i.getCreatedDate(), i.getTotalPrice() + oldValue);
            }else {
                map.put (i.getCreatedDate(), i.getTotalPrice());
            }
        });
        return convertListToMap(map);
    }

    public Map<LocalDate, Integer> getAllDailyBillForMerchant(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant not found");
        }

        int count=1;
        Map<LocalDate, Integer> map = new HashMap<>();
        merchant.getBill().forEach((i)->{
            if(map.containsKey(i.getCreatedDate())){
                int oldValue=map.get(i.getCreatedDate());
                map.replace(i.getCreatedDate(), oldValue+ 1);
            }else {
                map.put (i.getCreatedDate(), count);
            }
        });
        return map;
    }


    public List<String[]> convertListToMap(Map<LocalDate,Double> map){
        List<String[]> list = new ArrayList<>();
        for(Map.Entry m: map.entrySet()){
            list.add(new String[] {(String) m.getKey(), (String) m.getValue()});
        }
        return list;
    }
}