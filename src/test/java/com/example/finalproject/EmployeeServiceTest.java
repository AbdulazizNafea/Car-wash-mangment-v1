package com.example.finalproject;

import com.example.finalproject.model.Employee;
import com.example.finalproject.model.Feature;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeRepository employeeRepository;

    Employee employee1;

    @BeforeEach
    void setUp(){
        employee1 = new Employee(null, "ahmed", "0535862396");
    }

    @Test
    public void getEmployee() {
        employeeRepository.save(employee1);
        when(employeeRepository.findEmployeeById(employee1.getId())).thenReturn(employee1);
        Employee employee = employeeRepository.findEmployeeById(employee1.getId());
        //Note//read above note to understand
        Assertions.assertThat(employee1).isEqualTo(employee);

        verify(employeeRepository, times(1)).findEmployeeById(employee1.getId());
    }
}
