package com.example.vendingapp.controller;

import com.example.vendingapp.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RequestMapping("/api/sales")
@RequiredArgsConstructor
@RestController
public class SaleController {

    private final SaleService saleService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadSales(@RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(saleService.uploadSale(file));
    }

    @GetMapping("/amount")
    public ResponseEntity<Map<BigDecimal, Long>>
    getAmount(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fromDate,
              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime toDate) {
        return ResponseEntity.ok(saleService.getSale(fromDate, toDate));
    }

}
