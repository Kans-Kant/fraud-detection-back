package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.FraudStatsDto;
import com.isfa.fr.fraudDetection.repository.BankRepository;
import com.isfa.fr.fraudDetection.repository.CardRepository;
import com.isfa.fr.fraudDetection.repository.ClientRepository;
import com.isfa.fr.fraudDetection.repository.TransactionRepository;
import com.isfa.fr.fraudDetection.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Paths.DASHBOARD)
public class DashboardController {

    @Autowired
    private TransactionRepository txRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private BankRepository bankRepo;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/counts")
    public Map<String, Long> getCounts() {
        Map<String, Long> res = new HashMap<>();
        res.put("transactions", txRepo.count());
        res.put("frauds", txRepo.countByFraud(1L));
        res.put("clients", clientRepo.count());
        res.put("cards", cardRepo.count());
        res.put("banks", bankRepo.count());
        return res;
    }

    @GetMapping("/fraud-stats")
    public ResponseEntity<FraudStatsDto> getFraudStats() {
        return ResponseEntity.ok(transactionService.getFraudStats());
    }

}
