package com.lucas.company.service;

import com.lucas.company.model.Department;
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
    @DisplayName("Should post a new employee")
    void postCaseCorrect() {

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
    @DisplayName("Should not post a new employee because cpf has been registered")
    void postCaseNotCorrectCpfExists() {

        // Arrange
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findByCpf(employee.getCpf())).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("This CPF has already been used!");

        // Act
        ResponseEntity<Object> methodResult = employeeService.post(employeeDTO);

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findByCpf(any());
        verify(departmentRepository, times(1)).findById(any());

    }

    @Test
    @DisplayName("Should post create a new employee because the department hasn't been found")
    void postCaseNotCorrectDepartmentNotFound() {

        // Arrange
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findByCpf(employee.getCpf())).thenReturn(Optional.empty());
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("The department's id doesn't exist!");
        // Act
        ResponseEntity<Object> methodResult = employeeService.post(employeeDTO);

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findByCpf(any());
        verify(departmentRepository, times(1)).findById(any());

    }

    @Test
    @DisplayName("Should update employee.")
    void putCaseCorrect() {
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(employeeRepository.save(employee)).thenReturn(employee);

        ResponseEntity<Object> methodResult = employeeService.put(employeeDTO);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO(employee));

        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findById(any());
        verify(employeeRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should not put an employee because the employee wasn't found.")
    void putCaseEmployeeNotFound() {
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        ResponseEntity<Object> methodResult = employeeService.put(employeeDTO);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("Employee not found");

        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findById(any());
        verify(employeeRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should not put employee because the department of the employee doesn't exist.")
    void putCaseDepartmentNotFound() {
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());

        ResponseEntity<Object> methodResult = employeeService.put(employeeDTO);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("The department's id doesn't exist!");

        Assertions.assertEquals(methodResult, actualResult);
        verify(departmentRepository, times(1)).findById(any());
        verify(employeeRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should delete the employee")
    void deleteCaseCorrect() {
        // Arrange
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.OK).body("Employee deleted!");
        // Act
        ResponseEntity<Object> methodResult = employeeService.delete(employee.getId());

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should not delete the employee because the id wasn't found")
    void deleteCaseNotCorrect() {
        // Arrange2
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("Employee not found!");
        // Act
        ResponseEntity<Object> methodResult = employeeService.delete(employee.getId());

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findById(any());
    }

}