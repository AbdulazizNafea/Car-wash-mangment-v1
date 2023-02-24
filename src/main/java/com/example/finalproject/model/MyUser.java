package com.example.finalproject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
//@Table(name = "myUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate createdAt;
    private String role;


//    @OneToOne(cascade = CascadeType.ALL,mappedBy = "myUser")
////    @JoinColumn(name = "merchant_id", referencedColumnName="id")
//    @PrimaryKeyJoinColumn
//    private Merchant merchant;

//    @OneToOne
//    @MapsId
//    @JsonIgnore
//    private Merchant merchant;

//    @OneToOne
//    @MapsId
//    @JsonIgnore
//    private Customer customer;

    /*
    //this is correct
    @OneToOne
    @MapsId
    @JsonIgnore
    private Merchant merchant;

     */



    @OneToOne(cascade = CascadeType.ALL, mappedBy = "myUser")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "myUser")
    @PrimaryKeyJoinColumn
    private Merchant merchant;

}
