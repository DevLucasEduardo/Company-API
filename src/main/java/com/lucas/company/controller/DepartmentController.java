package com.lucas.company.controller;

import com.lucas.company.model.DepartmentDTO;
import com.lucas.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllDepartment() {
        return departmentService.getAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getDepartment(@PathVariable String name) {
        return departmentService.get(name);
    }

    @PostMapping("/")
    public ResponseEntity<Object> postDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.post(departmentDTO);
    }

    @PutMapping("/")
    public ResponseEntity<Object> putDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.put(departmentDTO);
    }

    @DeleteMapping("/")
    public ResponseEntity<Object> deleteDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.delete(departmentDTO);
    }

}
