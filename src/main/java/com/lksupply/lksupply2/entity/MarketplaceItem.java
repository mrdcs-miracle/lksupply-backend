package com.lksupply.lksupply2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "marketplace_items")
public class MarketplaceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // "Hybrid Paddy Seeds"
    private String price;       // "Rs. 450"
    private String type;        // "seeds", "fertilizer"
    private String description;

    @Column(length = 1000)
    private String imageUrl;    // URL to image
}
