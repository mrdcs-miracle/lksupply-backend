package com.lksupply.lksupply2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;   // e.g., "Samba Rice"
    private String category;   // "Rice", "Veg", "Sugar", "Salt"
    private String location;   // "Polonnaruwa"
    private int quantity;      // kg
    private String status;     // "Good", "Low", "Critical"
    private Double price;
}
