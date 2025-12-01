package com.isfa.fr.fraudDetection.repository;

import com.isfa.fr.fraudDetection.model.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    long countByFraud(Long fraud);

    @Query("SELECT AVG(t.amount) FROM Transaction t")
    Double findAvgAmount();

    @Query("SELECT MAX(t.amount) FROM Transaction t")
    Double findMaxAmount();

    @Query("""
        SELECT DATE(t.transactionTime), COUNT(t)
        FROM Transaction t
        WHERE t.fraud = 1
        GROUP BY DATE(t.transactionTime)
        ORDER BY DATE(t.transactionTime)
    """)
    List<Object[]> countDailyFraud();

    @Query("""
        SELECT t.transactionType, COUNT(t)
        FROM Transaction t
        WHERE t.fraud = 1
        GROUP BY t.transactionType
    """)
    List<Object[]> fraudCountByTransactionType();

    @Query("""
    SELECT t FROM Transaction t
    WHERE 
          t.amount > 1000
       OR FUNCTION('DATE_PART', 'hour', t.transactionTime) < 5
       OR FUNCTION('DATE_PART', 'hour', t.transactionTime) > 22
       OR t.transactionType = com.isfa.fr.fraudDetection.model.enums.TransactionType.INTERNATIONAL
       OR t.gloStep > 100""")
    Page<Transaction> findSuspiciousTransactions(Pageable pageable);

    @Query("""
    SELECT t FROM Transaction t
    WHERE 
          t.amount > 1000
       OR FUNCTION('DATE_PART', 'hour', t.transactionTime) < 5
       OR FUNCTION('DATE_PART', 'hour', t.transactionTime) > 22
       OR t.transactionType = com.isfa.fr.fraudDetection.model.enums.TransactionType.INTERNATIONAL
       OR t.gloStep > 100""")
    List<Transaction> findSuspiciousTransactions();
}
