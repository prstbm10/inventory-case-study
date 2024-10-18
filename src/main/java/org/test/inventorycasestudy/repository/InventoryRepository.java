package org.test.inventorycasestudy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.test.inventorycasestudy.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Page<Inventory> findAllByNamaBarangContainingIgnoreCase(String name, Pageable pageable);

}
