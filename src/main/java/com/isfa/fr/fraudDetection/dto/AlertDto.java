package com.isfa.fr.fraudDetection.dto;

public class AlertDto {

    private String reason;
    private double severity;

    public AlertDto(String reason, double severity) {
        this.reason = reason;
        this.severity = severity;
    }

    // Getters et Setters
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public double getSeverity() { return severity; }
    public void setSeverity(double severity) { this.severity = severity; }

}
