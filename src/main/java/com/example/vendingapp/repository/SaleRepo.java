package com.example.vendingapp.repository;

import com.example.vendingapp.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<Sale, Long> {

    List<Sale> getSaleByDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
