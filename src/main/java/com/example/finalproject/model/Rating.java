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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Rate must not be empty")
    @Min(value = 0, message = "min 0")
    @Max(value = 5, message = "Max 5")
    private double rate;
    @NotEmpty(message = "Comment must not be empty")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName ="id")
    @JsonIgnore
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName ="id")
    @JsonIgnore
    private Employee employee;

    @OneToOne
//    @MapsId
    @JsonIgnore
    private Bill bill;
}
