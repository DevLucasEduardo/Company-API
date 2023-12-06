package com.lucas.company.service;

import com.lucas.company.model.Department;
import com.lucas.company.model.DepartmentDTO;
import com.lucas.company.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public ResponseEntity<Object> get(String name) {
        Optional<Department> departmentValidation = departmentRepository.findByName(name);
        if (departmentValidation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new DepartmentDTO(departmentValidation.get()));
    }

    public ResponseEntity<Object> getAll() {
        List<Department> departmentValidation = departmentRepository.findAll();
        if (departmentValidation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(departmentValidation.stream().map(DepartmentDTO::new).toList());
    }
    @Transactional
    public ResponseEntity<Object> post(DepartmentDTO departmentDTO) {
        var department = converterDTO(departmentDTO);
        var departmentValidation = departmentRepository.findByName(department.getName());
        if (departmentValidation.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Department already registered!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new DepartmentDTO(departmentRepository.save(department)));
    }
    @Transactional
    public ResponseEntity<Object> put(DepartmentDTO departmentDTO) {
        var department = converterDTO(departmentDTO);
        var departmentValidation = departmentRepository.findByName(department.getName());
        if (departmentValidation.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new DepartmentDTO(departmentRepository.save(department)));

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Department wasn't found");
    }
    @Transactional
    public ResponseEntity<Object> delete(DepartmentDTO departmentDTO) {
        var department = converterDTO(departmentDTO);
        var departmentValidation = departmentRepository.findByName(department.getName());
        departmentRepository.delete(department);
        if (departmentValidation.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted!");

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Department wasn't found");
    }

    public Department converterDTO(DepartmentDTO departmentDTO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentDTO, department);
        return department;
    }
}
