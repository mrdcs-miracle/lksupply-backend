package com.lksupply.lksupply2.service;

import com.lksupply.lksupply2.entity.ActivityLog;
import com.lksupply.lksupply2.entity.Stock;
import com.lksupply.lksupply2.repository.ActivityLogRepository;
import com.lksupply.lksupply2.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockService {
    @Autowired private StockRepository stockRepo;
    @Autowired private ActivityLogRepository logRepo;

    public List<Stock> getAllStocks() { return stockRepo.findAll(); }

    public Stock saveStock(Stock stock) {
        // 🔴 FIX: Removed auto-calculation logic.
        // Now it will trust whatever status ("Critical", "Low", "Good") comes from the Frontend.

        /* PREVIOUS LOGIC (CAUSING THE ISSUE):
           stock.setStatus(stock.getQuantity() < 100 ? "Critical" : stock.getQuantity() < 500 ? "Low" : "Good");
        */

        // Save the stock exactly as provided
        Stock saved = stockRepo.save(stock);

        // Auto Log Activity
        ActivityLog log = new ActivityLog();
        log.setAction("Stock Update");
        log.setDetails("Updated " + stock.getItemName() + " (" + stock.getStatus() + ")");
        log.setStatus("Success");
        logRepo.save(log);

        return saved;
    }

    public void deleteStock(Long id) { stockRepo.deleteById(id); }

    // Helper to fetch single stock (useful for edits)
    public Stock getStockById(Long id) {
        return stockRepo.findById(id).orElse(null);
    }

    // Add Search if needed by controller
    public List<Stock> searchByCategory(String category) {
        return stockRepo.findByCategory(category);
    }

    // Add Update logic to ensure ID is handled
    public Stock updateStock(Long id, Stock stock) {
        stock.setId(id);
        return saveStock(stock);
    }
}