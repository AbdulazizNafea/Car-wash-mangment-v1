package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Brand must not be empty")
    private String brand;
    @NotEmpty(message = "Car type must not be empty")
    private String carType;
    @NotNull(message = "Model must not be empty")
    @Min(value = 4, message = "Mode min 4")
    @Max(value = 4, message = "Model Max 4")
    private Integer model;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName ="id")
    @JsonIgnore
    private Customer customer;

    public Car(Object o, String toyota, String suv, int i) {
    }
}
