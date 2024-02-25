package com.lucas.company.service;

import com.lucas.company.model.Department;
import com.lucas.company.model.DepartmentDTO;
import com.lucas.company.model.Employee;
import com.lucas.company.repository.DepartmentRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest{

    @Mock
    DepartmentRepository departmentRepository;

    @Autowired
    @InjectMocks
    DepartmentService departmentService;

    DepartmentDTO departmentDTO;
    Department department;

    @BeforeEach
    public void setUp() {
        List<Employee> employeeList = new ArrayList<>();
        departmentDTO = new DepartmentDTO(1L, "IT", employeeList);
        department = new Department(1L, "IT", employeeList);
    }

    @Test
    @DisplayName("Should post a new department.")
    void postCaseCorrect() {
        // Arrange
        Department department = departmentService.converterDTO(departmentDTO);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CREATED).body(new DepartmentDTO(department));
        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.empty());
        when(departmentRepository.save(department)).thenReturn(department);

        // Act
        ResponseEntity<Object> methodResult = departmentService.post(departmentDTO);

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("Should not post a new department because the department name has been used.")
    void postCaseNotCorrect() {
        Department department = departmentService.converterDTO(departmentDTO);
        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.of(department));

        ResponseEntity<Object> methodResult = departmentService.post(departmentDTO);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body(("This name has already been used!"));
        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("Should update department because the department name has been found.")
    void putCaseCorrect() {
        Department department = departmentService.converterDTO(departmentDTO);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);

        ResponseEntity<Object> methodResult = departmentService.put(departmentDTO);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.OK).body(new DepartmentDTO(department));

        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should not put a new department because the department name hasn't been found.")
    void putCaseNotCorrect() {
        Department department = departmentService.converterDTO(departmentDTO);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());

        ResponseEntity<Object> methodResult = departmentService.put(departmentDTO);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("Department not found!");
        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should delete a department.")
    void deleteCaseCorrect() {
        var id = department.getId();
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        ResponseEntity<Object> methodResult = ResponseEntity.status(HttpStatus.OK).body("Department deleted!");
        ResponseEntity<Object> actualResult = departmentService.delete(id);

        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should not delete a department because the id wasn't found")
    void deleteCaseNotCorrect() {
        var id = department.getId();
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> methodResult = departmentService.delete(id);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("Department not found!");
        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findById(id);
    }

}