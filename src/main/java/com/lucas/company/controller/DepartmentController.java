package com.lucas.company.controller;

import com.lucas.company.model.DepartmentDTO;
import com.lucas.company.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Operation(method = "GET", description = "Returns all registered departments.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the departments."),
            @ApiResponse(responseCode = "404", description = "No departments were found.")
    })
    @GetMapping("/")
    public ResponseEntity<Object> getAllDepartment() {
        return departmentService.getAll();
    }

    @Operation(method = "GET", description = "Returns a department by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the department."),
            @ApiResponse(responseCode = "404", description = "The department wasn't found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartment(@PathVariable Long id) {
        return departmentService.get(id);
    }

    @Operation(method = "POST", description = "Creates a new department. " +
            "The same name can't be repeated for two or more departments.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Returns the department created."),
            @ApiResponse(responseCode = "409", description = "The department's name has been used.")
    })
    @PostMapping("/")
    public ResponseEntity<Object> postDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.post(departmentDTO);
    }

    @Operation(method = "PUT", description = "Updates a department.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the updated department."),
            @ApiResponse(responseCode = "409", description = "Department not found.")
    })
    @PutMapping("/")
    public ResponseEntity<Object> putDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.put(departmentDTO);
    }

    @Operation(method = "DELETE", description = "Deletes a department by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The department has been deleted."),
            @ApiResponse(responseCode = "404", description = "The department wasn't found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long id) {
        return departmentService.delete(id);
    }

}
