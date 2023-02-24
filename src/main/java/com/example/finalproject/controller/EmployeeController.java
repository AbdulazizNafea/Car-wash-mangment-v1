package com.example.finalproject.controller;

import com.example.finalproject.model.Employee;
import com.example.finalproject.model.Feature;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    @GetMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Employee employee) {
        employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid Employee employee, @PathVariable Integer id) {
        employeeService.update(employee, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("UPDATED");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED");
    }

    //////////////////////////////////
    //Assign here
    @PostMapping("/addEmpolyee/{branchId}")
    public ResponseEntity addCustomer(@RequestBody @Valid Employee employee, @PathVariable Integer branchId) {
        employeeService.addEmployeeToBranch(employee,branchId);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }
}
