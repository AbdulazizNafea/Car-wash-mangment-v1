package com.example.finalproject.service;

import com.example.finalproject.DTO.MerchantDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Employee;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.MyUserRepository;
import com.example.finalproject.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final MyUserRepository myUserRepository;
    private final EmployeeRepository employeeRepository;

    public List<Merchant> getAll() {
        return merchantRepository.findAll();
    }

    public Merchant get(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        if (myUser == null || myUser.getMerchant().getId() == null) {
            throw new ApiException("merchant not found");
        }
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("merchant not found");
        }
        return merchant;
    }

    public void update(Merchant newMerchant, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant ID not found");
        }
        merchant.setCommercial_record(newMerchant.getCommercial_record());
        merchant.setCompany_name(newMerchant.getCompany_name());
        merchantRepository.save(merchant);
    }

    public void delete(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant == null) {
            throw new ApiException("Merchant ID not found");
        }
        merchantRepository.delete(merchant);
    }
    ///////////////////////////////////////////////////////////////////////////
    //All assign

    public void assignMyUserToMerchant2(MerchantDTO md, Integer auth) {

        MyUser myUser = myUserRepository.findMyUserById(auth);

        if (myUser.getMerchant() != null) {
            throw new ApiException("Merchants Already Existing !");
        } else if (myUser == null) {
            throw new ApiException("user ID not found");
        }

        Merchant myMerchant = new Merchant(null, md.getCompany_name(), md.getCommercial_record(), myUser, null, null, null);
        merchantRepository.save(myMerchant);
        myUserRepository.save(myUser);
    }


    //challenge 2-
    public void registerEmployeeAsCashier(MyUser user, Integer employeeId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);

        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (myUser.getMerchant() == null) {
            throw new ApiException("cashier not allow to create user ");
        }
        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant.getMyUser().getId() != auth) {
            throw new ApiException(" not allow to create user not under you");
        } else if (employee == null) {
            throw new ApiException("Employee not found");
        } else if (employee.getBranch().getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("you dont have access");
        } else if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            throw new ApiException("Password should be not empty and more than 3");
        }

        LocalDate date = LocalDate.now();
        user.setCreatedAt(date);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEnable(true);
        user.setRole("Cashier");
        employee.setMyUser(user);
        user.setEmployee(employee);
        myUserRepository.save(user);
        employeeRepository.save(employee);

    }

    //challenge 3-
    public void updateEmployCashier(MyUser user, Integer employeeId, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);

        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if(employee == null){
            throw new ApiException("user not found");
        }
        MyUser cashier = myUserRepository.findMyUserById(employee.getMyUser().getId());
        if (myUser.getMerchant() == null) {
            throw new ApiException("not allow to update your account ");
        }else if(cashier == null){
            throw new ApiException("user not found");
        }


        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant.getMyUser().getId() != auth) {
            throw new ApiException(" not allow to update user not under you");
        } else if (employee == null) {
            throw new ApiException("Employee not found");
        } else if (employee.getBranch().getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("you dont have access");
        } else if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            throw new ApiException("Password should be not empty and more than 3");
        }

        cashier.setUsername(user.getUsername());
        cashier.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        cashier.setPhone(user.getPhone());
        cashier.setEmail(user.getEmail());
        cashier.setEnable(user.isEnabled());
        cashier.setRole("Cashier");
        myUserRepository.save(cashier);
    }

    public void deleteCashier(Integer auth,Integer employeeId) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null){
            throw new ApiException("employee not found");
        }
        MyUser cashier = myUserRepository.findMyUserById(employee.getMyUser().getId());
        if (myUser.getMerchant() == null) {
            throw new ApiException("not allow to delete your account ");
        }else if(cashier == null){
            throw new ApiException("user not found");
        }


        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
        if (merchant.getMyUser().getId() != auth) {
            throw new ApiException(" not allow to delete user not under you");
        } else if (employee == null) {
            throw new ApiException("Employee not found");
        } else if (employee.getBranch().getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("you dont have access");
        }
        cashier.setEmployee(null);
        employee.setMyUser(null);
        myUserRepository.save(cashier);
        employeeRepository.save(employee);

        myUserRepository.delete(cashier);
    }



}
