package com.example.finalproject.service;


import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Author : Abdulaziz N. Alamri
 * 04-03-2023
 * it does not finish yet.
 * line 165 coming back to you.
 * ---------------------------------------
 * A يارب ارفع عني حمل التفكير و الترتيب
 * A ودبر لي أمري فإني لا أحسن التدبير
 * ---------------------------------------
 * A الكلاس هذا يحتاج تنظيف بخار مع فصل الكاشير عن التاجر عشان يسير كلاسي المفضل -
 * A بنيتك بدون تخطيط مسبق يا عزيزي هذي غلطتي وراح اصححها في الويكند او اذا فضيتلك -
 * A فكرة عالطاير - اسوي كلاس نظيف واحط فيه اشياء الكاشير بس أو احطها كلها بكلاس الموظفين -
 * A لازم ارجع ارسم يوزكيس عشان يوضح الطريق من جديد
 * A فكرة سيئة جدا أكنسل كل شي وأبدا مشروع ثاني صغير وغير معقد - تحتاج الى استشارة انسان اسطوري(عبدالرحمن ، عبدالله ، مجد ، مها)
 * A انتهى اليوم 05-03-2023
 */

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

    public void add(Bill bill,Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        if(myUser.getRole().equalsIgnoreCase("Cashier")){
            bill.setMerchant(myUser.getEmployee().getBranch().getMerchant());
        }else if(myUser.getRole().equalsIgnoreCase("merchant")){
            bill.setMerchant(myUser.getMerchant());
        }
        bill.setCreatedDate(LocalDate.now());
        billRepository.save(bill);
    }

    public void update(Bill newBill, Integer id, Integer auth) {
        Bill bill = billRepository.findBillById(id);
        if (bill == null) {
            throw new ApiException("bill ID not found");
        } else if (bill.getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("not authorize");
        }
        bill.setTotalPrice(newBill.getTotalPrice());
        bill.setPaymentMethod(newBill.getPaymentMethod());
        bill.setTotalPoints(newBill.getTotalPoints());
        billRepository.save(bill);
    }

    // الحذف ما ينفع بفكر بطريقة غير الحذف
    // ممكن دبل أشيك على الفاتورة اذا كانت فاضية أسمح له يحذفها
    // ممكن اسويلها enable=false ممكن اسويلها الغاء للعلاقة
    // أبعتذر عن كل شي الا الحذف ما للحذف عندي عذر

//    public void delete(Integer id) {
//        Bill bill = billRepository.findBillById(id);
//        if (bill == null) {
//            throw new ApiException("Feature ID not found");
//        }
//        billRepository.delete(bill);
//    }

    ///////////////////////////////////////////////////
    // assign here

    //Merchant And Cashier can add services in Bill.
    //
    public void addServicesToBill(Integer serviceId, Integer billId, Integer branchId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Branch branch = branchRepository.findBranchById(branchId);
        Bill bill = billRepository.findBillById(billId);
        Employee employee = null;
        ServicesProduct sp = servicesProductRepository.findServicesProductById(serviceId);
        if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (branch == null) {
            throw new ApiException("branch ID not found");
        }
        else if (sp == null) {
            throw new ApiException("Services Product ID not found");
        } else if (!branch.getServicesProducts().contains(sp)) {
            throw new ApiException("Sorry , Service not found in this Branch: " + branch.getName());
        }

        if (myUser.getRole().equalsIgnoreCase("Cashier")) {
            for (Employee i : sp.getBranch().getEmployees()) {
                if (i.getMyUser().getId() == auth) {
                    employee = employeeRepository.findEmployeeById(i.getId());
                }
            }
            if (employee.getMyUser().getId() != auth) {
                throw new ApiException("Sorry , You do not have the Authority !");
            }


        } else if (myUser.getRole().equalsIgnoreCase("merchant")) {
            if (sp.getBranch().getMerchant().getMyUser().getId() != auth) {
                throw new ApiException("Sorry , You do not have the Authority !");
            }
        }

        bill.setCreatedDate(LocalDate.now());
        sp.getBill().add(bill);
        bill.getServicesProducts().add(sp);
        servicesProductRepository.save(sp);
        billRepository.save(bill);
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Az عشان ما أنساها : يبغالها سكيورتي واسمح للكاشير والتاجر يستخدموها بدون مشاكل +
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //I will break my last timing from challenge 3 here in this endPoint.
    //start at 08:03 pm
    //end at 08:12pm
    //done
    public void removeServicesFromBill(Integer serviceId, Integer billId, Integer auth) {
        Merchant merchant;
        Employee cashier;
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Bill bill = billRepository.findBillById(billId);
        ServicesProduct sp = servicesProductRepository.findServicesProductById(serviceId);
        if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (sp == null) {
            throw new ApiException("Services Product ID not found");
        } else if (bill.getServicesProducts().isEmpty()) {
            throw new ApiException("there is no Services Product to delete it");
        }
        //Cashier flow
        if (myUser.getRole().equalsIgnoreCase("Cashier")) {
            cashier=employeeRepository.findEmployeeById(myUser.getEmployee().getId());
            if (cashier == null) {
                throw new ApiException("Cashier not found");
            }else if(sp.getBranch().getMerchant().getMyUser().getId() != auth){
                throw new ApiException("this product not available in your branch ");
            }
        ////merchant flow
        } else if (myUser.getRole().equalsIgnoreCase("Merchant")) {
            merchant=merchantRepository.findMerchantById(myUser.getMerchant().getId());
            if (merchant == null) {
                throw new ApiException("merchant not found");
            }else if(sp.getBranch().getMerchant().getMyUser().getId() != auth){
                throw new ApiException("this product not available in your branch ");
            }
        }
        double totalPrice = bill.getTotalPrice();
        double totalPoints = bill.getTotalPoints();
        bill.setTotalPrice(totalPrice - sp.getPrice());
        bill.setTotalPoints(totalPoints - sp.getPoint());
        sp.getBill().remove(bill);
        bill.setServicesProducts(null);
        billRepository.save(bill);
        servicesProductRepository.save(sp);

    }

    //challenge 3- I will make this end point able to use for Merchant And Cashier. And they can assign Bill.
    //Be Strong
    //start 4:56 am
    //end 5:05 am
    // done in 9min -> good job
    public void addBillToCustomerAndMerchantAndEmployee(Integer customerId, Integer billId, Integer employeeId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Customer customer = customerRepository.findCustomerById(customerId);
        Bill bill = billRepository.findBillById(billId);
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        Employee cashier;
        Merchant merchant = null;
        Point point = null;
        if (bill == null) {
            throw new ApiException("Bill ID not found");
        } else if (customer == null) {
            throw new ApiException("Customer ID not found");
        } else if (employee == null) {
            throw new ApiException("Employee ID not found");
        }

        //if user cashier
        if (myUser.getRole().equalsIgnoreCase("cashier")) {
            cashier = employeeRepository.findEmployeeById(myUser.getEmployee().getId());
            if (cashier == null) {
                throw new ApiException("Employee not found");
            }
            merchant = merchantRepository.findMerchantById(cashier.getBranch().getMerchant().getId());
            if (merchant == null) {
                throw new ApiException("Merchant ID not found");
            } else if ( !cashier.getBranch().getEmployees().contains(employee)) {
                throw new ApiException("this employee not on your branch");
            }

            for (Employee i : employee.getBranch().getEmployees()) {
                if (i.getMyUser().getId() == auth) {
                    cashier = employeeRepository.findEmployeeById(i.getId());
                }
            }
            if (cashier.getMyUser().getId() != auth) {
                throw new ApiException("Sorry , You do not have the Authority !");
            }else if(!merchant.getBill().contains(bill)){
                throw new ApiException("bill not found in your account");
            }
            point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, cashier.getBranch().getMerchant().getId());
            if (point == null) {
                throw new ApiException("can't add points, please create loyalty point to Customer");
            }

            //if user merchant
        } else if (myUser.getRole().equalsIgnoreCase("Merchant")) {
            merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
            if (merchant == null) {
                throw new ApiException("Merchant ID not found");
            } else if (employee.getBranch().getMerchant().getMyUser().getId() != auth) {
                throw new ApiException("not auth");
            }else if(bill.getMerchant().getMyUser().getId() != auth){
                throw new ApiException("bill not found in your account");
            }
            point = pointRepository.findPointByCustomerIdAndMerchantId(customerId, merchant.getId());
            if (point == null) {
                throw new ApiException("can't add points, please create loyalty point to Customer");
            }
        }

        // Buy by Points
        int totalPointPrice = 0;
        if (bill.getPaymentMethod().equalsIgnoreCase("point")) {
            for (ServicesProduct sp : bill.getServicesProducts()) {
                totalPointPrice = sp.getTotalPoints() + totalPointPrice;
            }
            if (point.getPoints() >= totalPointPrice) {
                point.setPoints(point.getPoints() - totalPointPrice);
            } else {
                throw new ApiException(customer.getFirstName()+": point balance not enough to buy this bill");
            }
        } else {
            bill.setTotalPrice(0);
            bill.setTotalPoints(0);
            for (ServicesProduct ss : bill.getServicesProducts()) {
                bill.setTotalPrice(ss.getPrice() + bill.getTotalPrice());
                bill.setTotalPoints(ss.getPoint() + bill.getTotalPoints());
            }
        }

        bill.setCreatedDate(LocalDate.now());
        bill.setCustomer(customer);
        bill.setEmployee(employee);
        billRepository.save(bill);
        pointRepository.save(point);
    }

    //

//    public List<Bill> getBillByCreatedDateBetween(String start, String end) throws ParseException {
//        //format string to date
//        List<Bill> bills = billRepository.findAllByCreatedDateBetween(LocalDate.parse(start), LocalDate.parse(end));
//        return bills;
//    }


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
        int countBill = 0;

        for (Employee e : branch.getEmployees()) {
            if (!e.getBill().isEmpty()) {
                for (Bill bill : e.getBill()) {
                    totalIncome = totalIncome + bill.getTotalPrice();
                    countBill++;
                }
            }
        }
        return "#All Branch income is: " + totalIncome + " \t#Number of bill is: " + countBill;
    }

    //lovely end point,will come back to see you soon.
    public Map<LocalDate, Double> getAllDailyIncomeForMerchant(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant not found");
        }
        Map<LocalDate, Double> map = new HashMap<>();
        merchant.getBill().forEach((i) -> {
            if (map.containsKey(i.getCreatedDate())) {
                double oldValue = map.get(i.getCreatedDate());
                map.replace(i.getCreatedDate(), i.getTotalPrice() + oldValue);
            } else {
                map.put(i.getCreatedDate(), i.getTotalPrice());
            }
        });
        return map;
    }

    public Map<LocalDate, Integer> getAllDailyBillForMerchant(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant not found");
        }

        int count = 1;
        Map<LocalDate, Integer> map = new HashMap<>();
        merchant.getBill().forEach((i) -> {
            if (map.containsKey(i.getCreatedDate())) {
                int oldValue = map.get(i.getCreatedDate());
                map.replace(i.getCreatedDate(), oldValue + 1);
            } else {
                map.put(i.getCreatedDate(), count);
            }
        });
        return map;
    }

/////////////////////just for test in my mind\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //this function it will take Map and convert it to list
    // it working fine but issue in LocalDate not allow to convert it to string

//    public List<String[]> convertListToMap(Map<LocalDate,Double> map){
//        List<String[]> list = new ArrayList<>();
//        for(Map.Entry m: map.entrySet()){
//            list.add(new String[] {(String) m.getKey(), (String) m.getValue()});
//        }
//        return list;
//    }

}