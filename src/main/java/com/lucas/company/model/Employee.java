package com.lucas.company.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_employee")
    private UUID idEmployee;

    @Column(name = "employee_name", nullable = false, length = 150)
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "fk_department")
    private Department department;
}
