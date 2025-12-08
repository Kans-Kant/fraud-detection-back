package com.isfa.fr.fraudDetection.service.impl;

import com.isfa.fr.fraudDetection.dto.AlertDto;
import com.isfa.fr.fraudDetection.dto.DailyFraudDto;
import com.isfa.fr.fraudDetection.dto.FraudStatsDto;
import com.isfa.fr.fraudDetection.dto.TransactionTypeCountDto;
import com.isfa.fr.fraudDetection.model.entities.Card;
import com.isfa.fr.fraudDetection.model.entities.Client;
import com.isfa.fr.fraudDetection.model.entities.Merchant;
import com.isfa.fr.fraudDetection.model.entities.Transaction;
import com.isfa.fr.fraudDetection.model.enums.TransactionType;
import com.isfa.fr.fraudDetection.repository.CardRepository;
import com.isfa.fr.fraudDetection.repository.MerchantRepository;
import com.isfa.fr.fraudDetection.repository.TransactionRepository;
import com.isfa.fr.fraudDetection.service.TransactionService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CardRepository cardRepository;

    public Page<Transaction> getAllTransactionsWithScore(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);

        Page<Transaction> result = transactions.map(tx -> {
            this.detect(tx);
            return tx;
        });

        return result;
    }

    public List<Transaction> getAllTransactionsWithScore() {
        List<Transaction> transactions = transactionRepository.findAll();
        for(Transaction tx : transactions) {
            this.detect(tx); // calcule score et alertes à la volée
        }
        return transactions;
    }

    public Transaction addTransaction(Transaction tx) {
        Transaction savedTx = transactionRepository.save(tx);
        this.detect(savedTx); // calcul du score
        return savedTx;
    }

    @Override
    public int importTransactions(MultipartFile file) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] line;
            boolean isHeader = true;

            while ((line = csvReader.readNext()) != null) {

                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Parsing des colonnes
                Double amount = Double.parseDouble(line[0]);
                LocalDateTime time = LocalDateTime.parse(line[1]);
                TransactionType type = TransactionType.valueOf(line[2]);
                Long carteId = Long.parseLong(line[3]);
                Long marchandId = Long.parseLong(line[4]);

                // Récupérer les objets liés
                Card carte = cardRepository.findById(carteId)
                        .orElseThrow(() -> new RuntimeException("Carte not found: " + carteId));

                Merchant merchant = merchantRepository.findById(marchandId)
                        .orElseThrow(() -> new RuntimeException("Marchand not found: " + marchandId));

                // Création de la transaction
                Transaction tx = new Transaction();
                tx.setAmount(amount);
                tx.setTransactionTime(time);
                tx.setTransactionType(type);
                tx.setCard(carte);
                tx.setMerchant(merchant);

                // Insert en base
                Transaction saved = transactionRepository.save(tx);

                // Calcul du score à la volée
                this.detect(saved);

                count++;
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'import CSV : " + e.getMessage(), e);
        }

        return count;
    }

    public FraudStatsDto getFraudStats() {

        long total = transactionRepository.count();
        long frauds = transactionRepository.countByFraud(1L);

        double avgAmount = Optional.ofNullable(transactionRepository.findAvgAmount()).orElse(0.0);
        double maxAmount = Optional.ofNullable(transactionRepository.findMaxAmount()).orElse(0.0);

        double fraudRate = (total == 0) ? 0 : (frauds * 100.0 / total);

        // FORMAT daily fraud
        List<DailyFraudDto> dailyList = transactionRepository.countDailyFraud()
                .stream()
                .map(row -> new DailyFraudDto(
                        row[0].toString(),
                        (long) row[1]
                ))
                .toList();

        // FORMAT fraud by type
        List<TransactionTypeCountDto> typeCounts = transactionRepository.fraudCountByTransactionType()
                .stream()
                .map(row -> new TransactionTypeCountDto(
                        row[0].toString(),
                        (long) row[1]
                ))
                .toList();

        return new FraudStatsDto(
                total,
                frauds,
                fraudRate,
                avgAmount,
                maxAmount,
                dailyList,
                typeCounts
        );
    }

    public Page<Transaction> getFraudulentTransactions(Pageable pageable) {
        return transactionRepository.findSuspiciousTransactions(pageable);
    }

    @Override
    public List<Transaction> getFraudulentTransactions() {
        return transactionRepository.findSuspiciousTransactions();
    }

    public Transaction detect(Transaction tx) {
        List<AlertDto> alerts = new ArrayList<>();

        // Montant élevé
        if(tx.getAmount() > 1000) {
            alerts.add(new AlertDto("Montant élevé", 0.9));
        }

        // Transaction la nuit
        int hour = tx.getTransactionTime().getHour();
        if(hour < 5 || hour > 22) {
            alerts.add(new AlertDto("Transaction à heure inhabituelle", 0.7));
        }

        // Transaction internationale
        if(TransactionType.INTERNATIONAL.equals(tx.getTransactionType())) {
            alerts.add(new AlertDto("Transaction internationale suspecte", 0.8));
        }

        // Localisation inhabituelle (exemple simplifié)
        if(isLocationUnusual(tx)) {
            alerts.add(new AlertDto("Localisation inhabituelle", 0.8));
        }

        // Calcul du score global et stockage temporaire
        double score = alerts.stream().mapToDouble(AlertDto::getSeverity).max().orElse(0.0);
        tx.setFraudScore(score);

        return tx;
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Transaction getById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    // Méthode exemple pour vérifier localisation inhabituelle
    private boolean isLocationUnusual(Transaction tx) {
        Card carte = tx.getCard();
        if (carte == null || carte.getClient() == null) {
            return false; // pas assez d'infos pour évaluer
        }

        Client client = carte.getClient();

        // Récupérer l'historique des transactions récentes du client (ex. dernières 10 transactions)
        List<Transaction> recentTransactions = client.getCards().stream()
                .flatMap(c -> c.getTransactions().stream())
                .filter(t -> !t.getId().equals(tx.getId())) // exclure la transaction actuelle
                .sorted((t1, t2) -> t2.getTransactionTime().compareTo(t1.getTransactionTime())) // tri décroissant
                .limit(10)
                .toList();

        if (recentTransactions.isEmpty()) {
            return false; // pas assez de données pour comparer
        }

        double lat = tx.getMerchant().getLatitude();
        double lon = tx.getMerchant().getLongitude();

        for (Transaction recentTx : recentTransactions) {
            double recentLat = recentTx.getMerchant().getLatitude();
            double recentLon = recentTx.getMerchant().getLongitude();

            double distance = haversine(lat, lon, recentLat, recentLon);

            if (distance < 50) {
                return false;
            }
        }

        return true;
    }

    // Méthode utilitaire pour calculer la distance entre deux points GPS (en km)
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

}
