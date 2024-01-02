package com.lucas.company.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "employees")
public class Employee {

    //@Serial
   // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Long id;

    @Column(name = "name_employee", nullable = false, length = 150)
    private String name;

    @Column(name = "cpf_employee", nullable = false, unique = true, length = 30)
    private String cpf;

    @Column(name = "birth_date_employee", nullable = false)
    private LocalDate birthDate;

    @Column(name = "civil_status_employee", nullable = false, length = 30)
    private String civilStatus;

    @ManyToOne
    @JoinColumn(name = "fk_department")
    private Department department;
}
