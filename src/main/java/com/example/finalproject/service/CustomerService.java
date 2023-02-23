package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Employee;
import com.example.finalproject.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

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
}
