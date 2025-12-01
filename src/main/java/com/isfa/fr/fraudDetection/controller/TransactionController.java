package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.PageDto;
import com.isfa.fr.fraudDetection.model.entities.Transaction;
import com.isfa.fr.fraudDetection.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(Paths.TRANSACTION)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public PageDto<Transaction> getAllTransactions(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "50") int size) {
        Page<Transaction> transactionsPage = transactionService.getAllTransactionsWithScore(page, size);

        return new PageDto<>(
                transactionsPage.getContent(),
                transactionsPage.getNumber(),
                transactionsPage.getSize(),
                transactionsPage.getTotalElements(),
                transactionsPage.getTotalPages()
        );
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction tx) {
        return transactionService.addTransaction(tx);
    }

    // Exemple simplifié d'export CSV
    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"transactions.csv\"");

        List<Transaction> transactions = transactionService.getAllTransactionsWithScore();
        try (PrintWriter writer = response.getWriter()) {
            writer.println("id,amount,transactionType,fraudScore");
            for(Transaction tx : transactions) {
                writer.printf("%d,%.2f,%s,%.2f%n",
                        tx.getId(),
                        tx.getAmount(),
                        tx.getTransactionType(),
                        tx.getFraudScore());
            }
        }
    }

    // Exemple simplifié d'export CSV
    @GetMapping("/export-frauds")
    public void exportFraudsCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"transaction-frauds.csv\"");

        List<Transaction> transactions = transactionService.getFraudulentTransactions();

        try (PrintWriter writer = response.getWriter()) {
            writer.println("id,amount,transactionType,date");

            for (Transaction tx : transactions) {
                writer.printf("%d,%.2f,%s,%s%n",
                        tx.getId(),
                        tx.getAmount(),
                        tx.getTransactionType(),
                        dateFormatter.format(tx.getCreatedAt()));
            }
        }
    }


    @PostMapping("/import-transactions")
    public int importTransactions(@RequestParam("file") MultipartFile file) {
        return transactionService.importTransactions(file);
    }

    @GetMapping("/fraud")
    public Page<Transaction> getFraud(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        return transactionService.getFraudulentTransactions(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        transactionService.delete(id);
        return true;
    }

    @GetMapping("/{id}")
    public Transaction getById(@PathVariable Long id) {
        return transactionService.getById(id);
    }
}
