package com.lucas.company.service;

import com.lucas.company.model.Department;
import com.lucas.company.model.DepartmentDTO;
import com.lucas.company.model.Employee;
import com.lucas.company.model.EmployeeDTO;
import com.lucas.company.repository.DepartmentRepository;
import com.lucas.company.repository.EmployeeRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    DepartmentRepository departmentRepository;

    @Autowired
    @InjectMocks
    EmployeeService employeeService;

    EmployeeDTO employeeDTO;
    Department department;

    @BeforeEach
    public void setUp() {

        List<Employee> employeeList = new ArrayList<>();
        department = new Department(1L, "Back-end", employeeList);
        employeeDTO = new EmployeeDTO(1L, "Lucas", "123",
                LocalDate.parse("1998-01-01"), "single", department);
    }

    @Test
    @DisplayName("Should create a new employee")
    void post() {

        // Arrange
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findByCpf(employee.getCpf())).thenReturn(Optional.empty());
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(employeeRepository.save(employee)).thenReturn(employee);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeDTO(employee));

        // Act
        ResponseEntity<Object> methodResult = employeeService.post(employeeDTO);

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findByCpf(any());
        verify(departmentRepository, times(1)).findById(any());
        verify(employeeRepository, times(1)).save(any());


    }

    @Test
    void put() {
    }

    @Test
    void delete() {
    }

    @Test
    void converterDTO() {
    }
}