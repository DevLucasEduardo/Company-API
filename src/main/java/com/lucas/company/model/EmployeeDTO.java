package com.lucas.company.model;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeDTO(Long id, String name, String cpf, LocalDate birthDate, String civilStatus, Department department) {

    public EmployeeDTO(Employee employee) {
        this(
                employee.getId(),
                employee.getName(),
                employee.getCpf(),
                employee.getBirthDate(),
                employee.getCivilStatus(),
                employee.getDepartment());
    }
}
