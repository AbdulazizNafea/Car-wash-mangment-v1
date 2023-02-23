package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double rate;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName ="id")
    @JsonIgnore
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName ="id")
    @JsonIgnore
    private Employee employee;
}
