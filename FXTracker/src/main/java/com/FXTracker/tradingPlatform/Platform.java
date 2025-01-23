package com.FXTracker.tradingPlatform;

import com.FXTracker.asset.Stock;
import com.FXTracker.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
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
