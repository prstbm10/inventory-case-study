package org.test.inventorycasestudy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.test.inventorycasestudy.entity.Inventory;
import org.test.inventorycasestudy.entity.dto.CreateUpdateInv;
import org.test.inventorycasestudy.repository.InventoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class InventoryService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory createUpdateInventory(CreateUpdateInv inventory, MultipartFile image) throws IOException {
        Inventory inv;
        if (inventory.getIdBarang() != null) {
            inv = inventoryRepository.findById(inventory.getIdBarang()).orElseThrow();
            inv.setUpdatedBy(inventory.getUpdatedBy());
            inv.setUpdatedAt(inventory.getUpdatedAt());
        } else {
            inv = new Inventory();
            inv.setCreatedBy(inventory.getCreatedBy());
            inv.setCreatedAt(inventory.getCreatedAt());
        }

        if (image != null) {
            String mimeType = image.getContentType();

            if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
                throw new IllegalArgumentException("Only JPG and PNG files are allowed");
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = image.getOriginalFilename();
            assert fileName != null;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            inv.setGambarBarang(filePath.toString());
        }

        inv.setAdditionalInfo(inventory.getAdditionalInfo());
        inv.setNamaBarang(inventory.getNamaBarang());
        inv.setJumlahStokBarang(inventory.getJumlahStokBarang());
        inv.setNomorSeriBarang(inventory.getNomorSeriBarang());

        return inventoryRepository.save(inv);
    }

    public String deleteInv(Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return "deleted id " + id;
        } else {
            return "id does not exist";
        }
    }

    public Page<Inventory> findAll(int limit, String name) {
        Pageable pageable = PageRequest.of(limit-1, 10, Sort.by("idBarang"));
        if (name != null && !name.isEmpty()) {
            return inventoryRepository.findAllByNamaBarangContainingIgnoreCase(name, pageable);
        } else {
            return inventoryRepository.findAll(pageable);
        }
    }
}
