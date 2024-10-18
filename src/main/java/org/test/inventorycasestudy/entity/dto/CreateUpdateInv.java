package org.test.inventorycasestudy.entity.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class CreateUpdateInv {
    @Nullable
    private Long idBarang;

    private String namaBarang;
    private int jumlahStokBarang;
    private String nomorSeriBarang;
    private String additionalInfo;

    @Nullable
    private LocalDateTime createdAt;

    @Nullable
    private String createdBy;

    private LocalDateTime updatedAt;
    private String updatedBy;
}
