package com.example.vendingapp.controller;

import com.example.vendingapp.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/sales")
@RequiredArgsConstructor
@RestController
public class SaleController {

    private final SaleService saleService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadSales(@RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(saleService.uploadSale(file));
    }

}
