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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String brand;

    private String carType;

    private Integer model;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName ="id")
    @JsonIgnore
    private Customer customer;

}
