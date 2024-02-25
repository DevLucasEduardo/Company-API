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
    @DisplayName("Should create a new employee")
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
    @DisplayName("Should not create a new employee because the cpf has been already registered")
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
    @DisplayName("Should not create a new employee because the department's id wasn't found")
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
    @DisplayName("Should update an employee")
    void putCaseCorrect() {

        // Arrange
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(employeeRepository.save(employee)).thenReturn(employee);
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO(employee));

        // Act
        ResponseEntity<Object> methodResult = employeeService.put(employeeDTO);

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findById(any());
        verify(departmentRepository, times(1)).findById(any());
        verify(employeeRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("Should not update an employee because the department's id wasn't found")
    void putCaseNotCorrectEmployeeNotFound() {

        // Arrange
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("The department's id doesn't exist!");

        // Act
        ResponseEntity<Object> methodResult = employeeService.put(employeeDTO);

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findById(any());
        verify(departmentRepository, times(1)).findById(any());

    }

    @Test
    @DisplayName("Should not update an employee because the id wasn't found")
    void putCaseNotCorrectDepartmentNotFound() {

        // Arrange
        Employee employee = employeeService.converterDTO(employeeDTO);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        ResponseEntity<Object> actualResult = ResponseEntity.status(HttpStatus.CONFLICT).body("Employee not found");

        // Act
        ResponseEntity<Object> methodResult = employeeService.put(employeeDTO);

        // Assert
        Assertions.assertEquals(methodResult, actualResult);
        verify(employeeRepository, times(1)).findById(any());
        verify(departmentRepository, times(1)).findById(any());

    }

    @Test
    @DisplayName("Should update an employee")
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
    @DisplayName("Should not delete an employee because the id wasn't found")
    void deleteCaseNotCorrect() {
        // Arrange
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