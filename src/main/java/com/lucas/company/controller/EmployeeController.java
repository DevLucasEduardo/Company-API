package com.lucas.company.controller;

import com.lucas.company.model.EmployeeDTO;
import com.lucas.company.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Operation(method = "GET", description = "Returns all registered employees.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the employees."),
            @ApiResponse(responseCode = "404", description = "No employees were found.")
    })
    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployees() {
        return employeeService.getAll();
    }

    @Operation(method = "GET", description = "Returns a employee by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the employee."),
            @ApiResponse(responseCode = "404", description = "The employee wasn't found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable Long id) {
        return employeeService.get(id);
    }

    @Operation(method = "POST", description = "Creates a new employee. " +
            "The same cpf can't be repeated for two or more employees.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Returns the employee created."),
            @ApiResponse(responseCode = "404", description = "The employee's associated department doesn't exist."),
            @ApiResponse(responseCode = "409", description = "The employee's cpf has been used.")
    })
    @PostMapping("/")
    public ResponseEntity<Object> postEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.post(employeeDTO);
    }

    @Operation(method = "PUT", description = "Updates an employee.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the updated employee."),
            @ApiResponse(responseCode = "409", description = "Employee not found."),
            @ApiResponse(responseCode = "404", description = "Department not found.")
    })
    @PutMapping("/")
    public ResponseEntity<Object> putEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.put(employeeDTO);
    }

    @Operation(method = "DELETE", description = "Deletes an employee by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The employee has been deleted."),
            @ApiResponse(responseCode = "404", description = "The employee wasn't found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        return employeeService.delete(id);
    }

}
