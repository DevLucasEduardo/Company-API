package com.lucas.company.model;


import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "departments")
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "department_name", unique = true, nullable = false, length = 150)
    private String departmentName;
}
