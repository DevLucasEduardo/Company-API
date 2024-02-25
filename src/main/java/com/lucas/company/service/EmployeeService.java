package com.lucas.company.service;


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

    public ResponseEntity<Object> get(Long id) {
        Optional<Employee> employeeVerifier = employeeRepository.findById(id);
        if (employeeVerifier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO(employeeVerifier.get()));
    }
    public ResponseEntity<Object> getAll() {
        List<Employee> employeeVerifier = employeeRepository.findAll();
        if (employeeVerifier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeVerifier.stream().map(EmployeeDTO::new).toList());
    }
    @Transactional
    public ResponseEntity<Object> post(EmployeeDTO employeeDTO) {
        var employee = converterDTO(employeeDTO);
        var employeeVerifier = employeeRepository.findByCpf(employee.getCpf());
        var departmentVerifier = departmentRepository.findById(employee.getDepartment().getId());

        if (employeeVerifier.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This CPF has already been used!");
        }
        if(departmentVerifier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The department's id doesn't exist!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeDTO(employeeRepository.save(employee)));
    }
    @Transactional
    public ResponseEntity<Object> put(EmployeeDTO employeeDTO) {
        var employee = converterDTO(employeeDTO);
        var employeeVerifier = employeeRepository.findById(employee.getId());
        var departmentVerifier = departmentRepository.findById(employee.getDepartment().getId());

        if (departmentVerifier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The department's id doesn't exist!");
        }
        if (employeeVerifier.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO(employeeRepository.save(employee)));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee not found");
    }
    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        var employeeVerifier = employeeRepository.findById(id);
        if (employeeVerifier.isPresent()) {
            employeeRepository.delete(employeeVerifier.get());
            return ResponseEntity.status(HttpStatus.OK).body("Employee deleted!");

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee not found!");
    }
    public Employee converterDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

}
