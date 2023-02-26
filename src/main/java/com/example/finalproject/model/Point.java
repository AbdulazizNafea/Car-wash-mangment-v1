package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Points must not be empty")
    private double points;

    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName ="id")
    @JsonIgnore
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName ="id")
    @JsonIgnore
    private Customer customer;


}
