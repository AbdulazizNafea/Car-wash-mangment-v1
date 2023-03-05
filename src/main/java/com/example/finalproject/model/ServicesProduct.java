package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty(message = "Name must not be empty")
    private String name;
    @NotEmpty(message = "Description must not be empty")
    private String description;
    @NotNull(message = "Price must not be empty")
    private double price;
    @NotNull(message = "TotalPoints must not be empty")
    private Integer totalPoints;
    @NotNull(message = "Point must not be empty")
    private Integer point;




    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName ="id")
    @JsonIgnore
    private Branch branch;




    @ManyToMany
    @JsonIgnore
    private List<Bill> bill;

//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
//    private List<Feature> coupon;
}
