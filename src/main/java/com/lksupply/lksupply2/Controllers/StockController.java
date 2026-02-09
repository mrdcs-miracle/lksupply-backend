package com.lksupply.lksupply2.Controllers;

import com.lksupply.lksupply2.entity.Stock;
import com.lksupply.lksupply2.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class StockController {

    @Autowired
    private StockService service;

    @GetMapping
    public List<Stock> getAll() {
        return service.getAllStocks();
    }

    @PostMapping
    public Stock create(@RequestBody Stock stock) {
        return service.saveStock(stock);
    }

    @PutMapping("/{id}")
    public Stock update(@PathVariable Long id, @RequestBody Stock stock) {
        // This sets the ID from the URL path onto the stock object
        stock.setId(id);
        // The service then saves (updates) it in the database
        return service.saveStock(stock);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteStock(id);
    }
}