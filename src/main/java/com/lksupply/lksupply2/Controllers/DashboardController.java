package com.lksupply.lksupply2.Controllers;

import com.lksupply.lksupply2.entity.ActivityLog;
import com.lksupply.lksupply2.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DashboardController {

    @Autowired
    private ActivityLogRepository logRepo;

    // 🟢 KEEP: Logs are fine here
    @GetMapping("/logs/recent")
    public List<ActivityLog> getRecentLogs() {
        return logRepo.findTop10ByOrderByTimestampDesc();
    }

    // ❌ DELETED: "addItem" (Marketplace) logic.
    // Why? Because it is now inside MarketplaceController.java
}
