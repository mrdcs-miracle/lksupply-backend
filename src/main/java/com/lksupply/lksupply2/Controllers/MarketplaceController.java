package com.lksupply.lksupply2.Controllers;

import com.lksupply.lksupply2.entity.MarketplaceItem;
import com.lksupply.lksupply2.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/marketplace")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MarketplaceController {

    @Autowired
    private MarketplaceRepository marketplaceRepository;

    // Folder to save images
    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public List<MarketplaceItem> getAllProducts() {
        return marketplaceRepository.findAll();
    }

    // 🟢 ADD PRODUCT WITH IMAGE UPLOAD
    @PostMapping
    public MarketplaceItem addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("type") String type,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        String imageUrl = "";

        if (file != null && !file.isEmpty()) {
            // Ensure directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // Save the URL to access it later
            imageUrl = "http://localhost:8080/uploads/" + filename;
        }

        MarketplaceItem item = new MarketplaceItem();
        item.setName(name);
        item.setPrice(price);
        item.setType(type);
        item.setDescription(description);
        item.setImageUrl(imageUrl);

        return marketplaceRepository.save(item);
    }

    // 🟢 UPDATE PRODUCT WITH IMAGE UPLOAD
    @PutMapping("/{id}")
    public ResponseEntity<MarketplaceItem> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("type") String type,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        MarketplaceItem item = marketplaceRepository.findById(id).orElse(null);
        if (item == null) return ResponseEntity.notFound().build();

        item.setName(name);
        item.setPrice(price);
        item.setType(type);
        item.setDescription(description);

        // Only update image if a new file is uploaded
        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            item.setImageUrl("http://localhost:8080/uploads/" + filename);
        }

        return ResponseEntity.ok(marketplaceRepository.save(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        marketplaceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
