package com.example.myapplication;

public class TietKiemEntry {
    private String timestamp;
    private double amount;

    public TietKiemEntry() {
        // Empty constructor needed for Firebase
    }

    public TietKiemEntry(String timestamp, double amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }
}
