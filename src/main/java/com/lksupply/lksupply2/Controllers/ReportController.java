package com.lksupply.lksupply2.Controllers;

import com.lksupply.lksupply2.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ReportController {
    @Autowired private ReportService reportService;

    @GetMapping("/download/stock")
    public ResponseEntity<InputStreamResource> downloadReport() {
        InputStreamResource file = new InputStreamResource(reportService.generateStockReport());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stock_report.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
