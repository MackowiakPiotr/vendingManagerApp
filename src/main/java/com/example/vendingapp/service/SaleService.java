package com.example.vendingapp.service;

import com.example.vendingapp.Sale;
import com.example.vendingapp.repository.SaleRepo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaleService {

    private final SaleRepo saleRepo;

    public Integer uploadSale(MultipartFile file) throws IOException {

        List<Sale> sale = parseCSV(file);
        saleRepo.saveAll(sale);
        return sale.size();

    }

    private List<Sale> parseCSV(MultipartFile file) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            HeaderColumnNameMappingStrategy<SaleCSVRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(SaleCSVRepresentation.class);

            CsvToBean<SaleCSVRepresentation> csvToBean = new CsvToBeanBuilder<SaleCSVRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build();

            return csvToBean.parse()
                    .stream()
                    .map(csvline -> Sale.builder()
                            .date(csvline.getDate())
                            .state(csvline.getStatus())
                            .typeOfTransaction(csvline.getTransactionType())
                            .amount(csvline.getAmount())
                            .build())
                    .collect(Collectors.toList());

        }
    }

}
