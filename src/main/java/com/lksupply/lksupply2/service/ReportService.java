package com.lksupply.lksupply2.service;

import com.lksupply.lksupply2.entity.Stock;
import com.lksupply.lksupply2.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ReportService {
    @Autowired private StockRepository stockRepository;

    public ByteArrayInputStream generateStockReport() {
        List<Stock> stocks = stockRepository.findAll();
        StringBuilder csv = new StringBuilder("ID,Item Name,Location,Category,Quantity (kg),Price (1KG),Status\n");

        for (Stock s : stocks) {
            csv.append(s.getId()).append(",")
                    .append(s.getItemName()).append(",")
                    .append(s.getLocation()).append(",")
                    .append(s.getCategory()).append(",")
                    .append(s.getQuantity()).append(",")
                    .append(s.getPrice()).append(",") // 🟢 Added Price
                    .append(s.getStatus()).append("\n");
        }
        return new ByteArrayInputStream(csv.toString().getBytes(StandardCharsets.UTF_8));
    }
}
