package com.FXTracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "platform")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String platformName;
    @OneToMany
    private Set<Stock> stocks;
    @OneToMany
    private List<User> users;


}
