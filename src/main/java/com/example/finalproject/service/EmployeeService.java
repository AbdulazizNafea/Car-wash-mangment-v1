package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Employee;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }
    public Employee getById(Integer id){
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new ApiException("Employee not found");
        }
        return employee;
    }

    public void add(Employee employee){
        employeeRepository.save(employee);
    }

    public void update(Employee employee,Integer id) {
        Employee oldEmp= employeeRepository.findEmployeeById(id);
        if(oldEmp == null){
            throw new ApiException("Employee ID not found");
        }
//        oldEmp.setId(employee.getId());
        oldEmp.setName(employee.getName());
        oldEmp.setPhone(employee.getPhone());
        employeeRepository.save(oldEmp);
    }

    public void delete(Integer id) {
        Employee oldEmp= employeeRepository.findEmployeeById(id);
        if(oldEmp == null){
            throw new ApiException("Employee ID not found");
        }
        employeeRepository.delete(oldEmp);
    }
}
