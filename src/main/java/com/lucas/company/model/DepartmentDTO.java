package com.lucas.company.model;

import java.util.List;
import java.util.UUID;

public record DepartmentDTO(Long id, String name, List<Employee> employees) {

    public DepartmentDTO(Department department) {
        this(
                department.getId(),
                department.getName(),
                department.getEmployees()
        );
    }

}
