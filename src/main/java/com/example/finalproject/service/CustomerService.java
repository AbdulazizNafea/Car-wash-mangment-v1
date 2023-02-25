package com.example.finalproject.service;

import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final MyUserRepository myUserRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer get(Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Customer customer = customerRepository.findCustomerById(myUser.getCustomer().getId());
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        return customer;
    }


    public void add(Customer customer) {
        customerRepository.save(customer);
    }

    public void update(Customer customer, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Customer oldCustomer = customerRepository.findCustomerById(myUser.getCustomer().getId());
        if (oldCustomer == null) {
            throw new ApiException("Customer ID not found");
        } else if (oldCustomer.getMyUser().getId() != auth) {
            throw new ApiException("Sorry , You do not have the authority to update this Todo!");
        }
        oldCustomer.setFirstName(customer.getFirstName());
        oldCustomer.setLastName(customer.getLastName());
        oldCustomer.setAge(customer.getAge());
        oldCustomer.setGender(customer.getGender());
        oldCustomer.setMyUser(myUser);
        oldCustomer.getMyUser().setRole("Customer");
        customerRepository.save(oldCustomer);
    }

    public void delete(Integer id) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new ApiException("Customer ID not found");
        }
        customerRepository.delete(customer);
    }

    ///////////////////////////////////////////////////////////////////////////
    //All assign
    public void assignCustomerToMyUser(CustomerDTO cd, Integer auth) {
        MyUser myUser = myUserRepository.findMyUserById(auth);

        if (myUser == null) {
            throw new ApiException("user ID not found");
        } else if (myUser.getCustomer() != null) {
            throw new ApiException("Customer Already Exist!!!!");
        }

        Customer myCustomer = new Customer(null, cd.getFirstName(), cd.getLastName(), cd.getAge(), cd.getGender(), myUser, null, null, null);
        customerRepository.save(myCustomer);
    }
}
