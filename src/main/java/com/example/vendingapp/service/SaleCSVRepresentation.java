package com.example.vendingapp.service;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SaleCSVRepresentation {

    @CsvDate("yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "Data")
    private LocalDateTime date;

    @CsvBindByName(column = "Status")
    private String status;

    @CsvBindByName(column = "Rodzaj transakcji")
    private String transactionType;

    @CsvNumber("#,####")
    @CsvBindByName(column = "Kwota")
    private BigDecimal amount;

}
