package com.lksupply.lksupply2.Controller;

import com.lksupply.lksupply2.entity.*;
import com.lksupply.lksupply2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DashboardController {
    @Autowired
    private ActivityLogRepository logRepo;
    @Autowired
    private MarketplaceRepository marketRepo;

    // Logs
    @GetMapping("/logs/recent")
    public List<ActivityLog> getRecentLogs() {
        return logRepo.findTop10ByOrderByTimestampDesc();
    }

    // Marketplace
    @GetMapping("/marketplace")
    public List<MarketplaceItem> getItems() {
        return marketRepo.findAll();
    }

    // 🟢 UPDATED: Handle Image Upload + Data
    @PostMapping("/marketplace")
    public MarketplaceItem addItem(
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("type") String type,
            @RequestParam("desc") String desc,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        MarketplaceItem item = new MarketplaceItem();
        item.setName(name);
        item.setPrice(price);
        item.setType(type);
        item.setDescription(desc);

        if (file != null && !file.isEmpty()) {
            // Save file to "uploads" folder
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads");
            if (!Files.exists(path)) Files.createDirectories(path);
            Files.copy(file.getInputStream(), path.resolve(fileName));
            item.setImageUrl("http://localhost:8080/uploads/" + fileName); // Serve this via static config
        } else {
            item.setImageUrl("https://via.placeholder.com/150"); // Default
        }
        return marketRepo.save(item);
    }
}
