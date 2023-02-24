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
public class ServicesProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private double price;
    private Integer totalPoints;
    private Integer point;




    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName ="id")
    @JsonIgnore
    private Branch branch;




    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName ="id")
    @JsonIgnore
    private Bill bill;

//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
//    private List<Feature> coupon;
}
