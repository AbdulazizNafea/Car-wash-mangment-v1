package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private String firstName;

    private String lastName;

    private Integer age;

    private String gender;

    /////////////////////Relations\\\\\\\\\\\\\\\\\\\\\\
    @OneToOne
//    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Car> car;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
//    @PrimaryKeyJoinColumn
//    private MyUser myUser;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
//    private List<Point> point;
//
//

//
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Bill> bill;


}
