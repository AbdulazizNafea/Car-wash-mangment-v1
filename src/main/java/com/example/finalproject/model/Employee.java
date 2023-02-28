package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Name must not be empty")
    private String name;
    @NotEmpty(message = "Phone must not be empty")
    private String phone;

    private double avgRate;


    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName ="id")
    @JsonIgnore
    private Branch branch;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Rating> ratings;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "employee")
    //Remember if you want to delete any child use this
    // orphanRemoval=true
    // @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "bill" ,orphanRemoval=true)
    private List<Bill> bill;

    public Employee(Object o, String ahmed, String s) {
    }
}
