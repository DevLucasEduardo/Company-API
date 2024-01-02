package com.lucas.company.controller;

import com.lucas.company.model.EmployeeDTO;
import com.lucas.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Object> getEmployee(@PathVariable String cpf) {
        return employeeService.get(cpf);
    }

    @PostMapping("/")
    public ResponseEntity<Object> postEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.post(employeeDTO);
    }

    @PutMapping("/")
    public ResponseEntity<Object> putEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.put(employeeDTO);
    }

    @DeleteMapping("/")
    public ResponseEntity<Object> deleteEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.delete(employeeDTO);
    }

}
