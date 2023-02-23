package com.example.finalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String company_name;
    private String commercial_record;


//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "merchant")
//    @JoinColumn(name = "merchant_id")
//    @PrimaryKeyJoinColumn
//    private MyUser myUser;

//    @OneToOne
//    @MapsId
//    @JsonIgnore
//    private MyUser myUser;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Point> point;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Branch> branch;
}
