package com.example.bank.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String title;

    @Column
    private double lon;

    @Column
    private double lat;

    @Column
    private String address;

}
