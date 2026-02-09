package com.lksupply.lksupply2.repository;

import com.lksupply.lksupply2.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByCategory(String category);
}