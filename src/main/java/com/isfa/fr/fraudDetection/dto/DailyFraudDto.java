package com.isfa.fr.fraudDetection.dto;

public record DailyFraudDto(
        String date,
        long frauds
) {
}
