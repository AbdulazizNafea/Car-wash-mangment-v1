package com.example.finalproject.service;

import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Employee;
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

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }
    public Customer getById(Integer id){
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        return customer;
    }

    public void add(Customer customer){
        customerRepository.save(customer);
    }

    public void update(Customer customer,Integer id) {
        Customer oldCustomer= customerRepository.findCustomerById(id);
        if(oldCustomer == null){
            throw new ApiException("Customer ID not found");
        }
//        oldEmp.setId(employee.getId());
        oldCustomer.setFirstName(customer.getFirstName());
        oldCustomer.setLastName(customer.getLastName());
        oldCustomer.setAge(customer.getAge());
        oldCustomer.setGender(customer.getGender());
        customerRepository.save(oldCustomer);
    }

    public void delete(Integer id) {
        Customer customer= customerRepository.findCustomerById(id);
        if(customer == null){
            throw new ApiException("Customer ID not found");
        }
        customerRepository.delete(customer);
    }

    ///////////////////////////////////////////////////////////////////////////
    //All assign
    public void assignCustomerToMyUser(CustomerDTO cd){
        MyUser myUser = myUserRepository.findMyUserById(cd.getId());
        if(myUser == null){
            throw new ApiException("user ID not found");
        }
        if(!myUser.getRole().equalsIgnoreCase("Customer")){
            throw new ApiException("your role not Customer");
        }
        Customer myCustomer = new Customer(null,cd.getFirstName(),cd.getLastName(),cd.getAge(),cd.getGender(),myUser,null,null);
        customerRepository.save(myCustomer);
    }
}
