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


    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "bill")
    //Remember if you want to delete any child use this
    // orphanRemoval=true
    // @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "bill" ,orphanRemoval=true)
    private List<ServicesProduct> servicesProducts;





    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bill")
    @PrimaryKeyJoinColumn
    private Rating rating;


}
