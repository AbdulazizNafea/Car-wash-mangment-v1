package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    private double totalPoints;


    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName ="id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName ="id")
    @JsonIgnore
    private Merchant merchant;


    @ManyToMany( mappedBy = "bill")
    private List<ServicesProduct> servicesProducts;





    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bill")
    @PrimaryKeyJoinColumn
    private Rating rating;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName ="id")
    @JsonIgnore
    private Employee employee;


    public Bill(Object o, double v, String cash, double v1) {
    }
}
