package org.test.inventorycasestudy.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.test.inventorycasestudy.entity.dto.CreateUpdateInv;
import org.test.inventorycasestudy.service.InventoryService;

@RestController
@RequestMapping("api/v1/inv")
public class InventoryRestController {
    private static final Logger logger = LogManager.getLogger(InventoryRestController.class);

    private final InventoryService inventoryService;

    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("create")
    public ResponseEntity<Object> create(@RequestPart("data") CreateUpdateInv inventory, @RequestPart("gambarBarang") MultipartFile image) {
        try {
            logger.info("/create params {}", inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createUpdateInventory(inventory, image));
        } catch (Exception e) {
            logger.error("/create error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("edit")
    public ResponseEntity<Object> update(@RequestPart("data") CreateUpdateInv inventory, @RequestPart(value = "gambarBarang", required = false) MultipartFile image) {
        try {
            logger.info("/edit params {}", inventory);
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.createUpdateInventory(inventory, image));
        } catch (Exception e) {
            logger.error("/edit error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("data")
    public ResponseEntity<Object> getAllData(@RequestParam int page, @RequestParam(required = false) String name) {
        try {
            logger.info("/data/{}/{}", page, name);
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.findAll(page, name));
        } catch (Exception e) {
            logger.error("/data/{}/{} error {}", page, name, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id) {
        try {
            logger.info("/delete/{}", id);
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.deleteInv(id));
        } catch (Exception e) {
            logger.error("/delete/{} error {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
