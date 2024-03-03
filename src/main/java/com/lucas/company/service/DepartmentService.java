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

    public ResponseEntity<Object> get(Long id) {
        Optional<Department> departmentVerifier = departmentRepository.findById(id);
        if (departmentVerifier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new DepartmentDTO(departmentVerifier.get()));
    }

    public ResponseEntity<Object> getAll() {
        List<Department> departmentVerifier = departmentRepository.findAll();
        if (departmentVerifier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No departments found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(departmentVerifier.stream().map(DepartmentDTO::new).toList());
    }

    @Transactional
    public ResponseEntity<Object> post(DepartmentDTO departmentDTO) {
        var department = converterDTO(departmentDTO);
        var departmentVerifier = departmentRepository.findByName(department.getName());
        if (departmentVerifier.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This name has already been used!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new DepartmentDTO(departmentRepository.save(department)));
    }
    @Transactional
    public ResponseEntity<Object> put(DepartmentDTO departmentDTO) {
        var department = converterDTO(departmentDTO);
        var departmentVerifier = departmentRepository.findById(department.getId());
        if (departmentVerifier.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new DepartmentDTO(departmentRepository.save(department)));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Department not found!");
    }
    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        var departmentVerifier = departmentRepository.findById(id);
        if (departmentVerifier.isPresent()) {
            departmentRepository.delete(departmentVerifier.get());
            return ResponseEntity.status(HttpStatus.OK).body("Department deleted!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found!");
    }

    public Department converterDTO(DepartmentDTO departmentDTO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentDTO, department);
        return department;
    }
}
