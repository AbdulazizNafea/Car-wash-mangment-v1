package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name must not be empty")
    private String name;
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must not be null")
    private String email;
    @NotEmpty(message = "Phone must not be empty")
    private String phone;
    private LocalDate created;

    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @JsonIgnore
    private Merchant merchant;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Feature> features;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<ServicesProduct> servicesProducts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Rating> ratings;

//    @ManyToMany
//    @JsonIgnore
//    List<Customer> clint;
}
