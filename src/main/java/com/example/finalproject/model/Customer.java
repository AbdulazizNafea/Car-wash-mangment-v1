package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "First name must not be empty")
    private String firstName;
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;
    @NotNull(message = "Age must not be null")
    private Integer age;
    @NotEmpty(message = "Gender must not be empty")
    @Pattern(regexp = "(male|female)", message = "Gender must be ether Male or Female ")
    private String gender;

    /////////////////////Relations\\\\\\\\\\\\\\\\\\\\\\
    @OneToOne
//    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Car> car;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Point> point;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Bill> bill;


    public Customer(Object o, String hashem, String ali, int i, String male) {
    }
}
