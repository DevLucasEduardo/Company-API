package com.lucas.company.service;


import com.lucas.company.model.Department;
import com.lucas.company.model.Employee;
import com.lucas.company.model.EmployeeDTO;
import com.lucas.company.repository.DepartmentRepository;
import com.lucas.company.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public ResponseEntity<Object> get(String cpf) {
        Optional<Employee> employeeValidation = employeeRepository.findByCpf(cpf);
        if (employeeValidation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO(employeeValidation.get()));
    }
    public ResponseEntity<Object> getAll() {
        List<Employee> employeeValidation = employeeRepository.findAll();
        if (employeeValidation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeValidation.stream().map(EmployeeDTO::new).toList());
    }
    @Transactional
    public ResponseEntity<Object> post(EmployeeDTO employeeDTO) {
        var employee = converterDTO(employeeDTO);
        var employeeValidation = employeeRepository.findByCpf(employee.getCpf());
        if (employeeValidation.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee already registered!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeDTO(employeeRepository.save(employee)));
    }
    @Transactional
    public ResponseEntity<Object> put(EmployeeDTO employeeDTO) {
        var employee = converterDTO(employeeDTO);
        var employeeValidation = employeeRepository.findByCpf(employee.getCpf());
        if (employeeValidation.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO(employeeRepository.save(employee)));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee wasn't found");
    }
    @Transactional
    public ResponseEntity<Object> delete(EmployeeDTO employeeDTO) {
        var employee = converterDTO(employeeDTO);
        var employeeValidation = employeeRepository.findByCpf(employee.getCpf());
        if (employeeValidation.isPresent()) {
            employeeRepository.delete(employee);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted!");

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee wasn't found");
    }
    public Employee converterDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

}
