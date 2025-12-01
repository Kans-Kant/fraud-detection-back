package com.isfa.fr.fraudDetection.dto;

import java.util.List;

public record FraudStatsDto(
        long totalTransactions,
        long fraudulentTransactions,
        double fraudRate,
        double avgAmount,
        double maxAmount,
        List<DailyFraudDto> dailyFrauds,
        List<TransactionTypeCountDto> typeCounts
) {
}
