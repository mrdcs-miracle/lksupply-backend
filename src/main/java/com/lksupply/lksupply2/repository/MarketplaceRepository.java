package com.lksupply.lksupply2.repository;

import com.lksupply.lksupply2.entity.MarketplaceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<MarketplaceItem, Long> {
    // This fixes the "Cannot resolve symbol" and "Could not autowire" errors
}
