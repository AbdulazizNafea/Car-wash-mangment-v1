package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double totalPrice;

    private String paymentMethod;

    private Integer totalPoints;

    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName ="id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "servicesProduct_id", referencedColumnName ="id")
    @JsonIgnore
    private ServicesProduct servicesProduct;

//Abdulaziz here.
//aziz29

//    @OneToOne(cascade = CascadeType.ALL , mappedBy = "carWash")
//    @PrimaryKeyJoinColumn
//    private Employee employee;

//    @OneToOne
//    @MapsId
//    @JsonIgnore
//    private Employee employee;

//    @OneToOne
//    @MapsId
//    @JsonIgnore
//    private Rating rating;


}
