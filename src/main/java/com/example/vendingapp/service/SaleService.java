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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    public Map<BigDecimal, Long> getSale(LocalDateTime fromDate, LocalDateTime toDate) {

        List<Sale> list = saleRepo.getSaleByDateBetween(fromDate, toDate);
        List<BigDecimal> amounts = list.stream().map(sale -> sale.amount).toList();

        Map<BigDecimal, Long> unsortedAmounts = amounts.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return unsortedAmounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(TreeMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);

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
                    .map(csvLine -> Sale.builder()
                            .date(csvLine.getDate())
                            .state(csvLine.getStatus())
                            .typeOfTransaction(csvLine.getTransactionType())
                            .amount(csvLine.getAmount())
                            .build())
                    .collect(Collectors.toList());
        }
    }

}
