package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "TotalPrice must not be empty")
    private double totalPrice;
    @NotEmpty(message = "PaymentMethod must not be empty")
    private String paymentMethod;
    @NotNull(message = "TotalPoints must not be empty")
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


    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "bill")
    //Remember if you want to delete any child use this
    // orphanRemoval=true
    // @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "bill" ,orphanRemoval=true)
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
