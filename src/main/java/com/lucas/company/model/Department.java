package com.lucas.company.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "departments")
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_department")
    private UUID id;

    @Column(name = "department_name", unique = true, nullable = false, length = 150)
    private String name;

    @OneToMany
    @JoinColumn(name = "fk_department")
    private List<Employee> employees;
}
