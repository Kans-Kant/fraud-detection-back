package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.dto.AlertDto;
import com.isfa.fr.fraudDetection.dto.FraudStatsDto;
import com.isfa.fr.fraudDetection.model.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TransactionService {

    Page<Transaction> getAllTransactionsWithScore(int page, int size);
    List<Transaction> getAllTransactionsWithScore();
    Transaction addTransaction(Transaction tx);
    int importTransactions (MultipartFile file);

    FraudStatsDto getFraudStats();

    Page<Transaction> getFraudulentTransactions(int page, int size);
    List<Transaction> getFraudulentTransactions();

    Transaction detect(Transaction tx);

    void delete(Long id);
    Transaction getById(Long id);

}
