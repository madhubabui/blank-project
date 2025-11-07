package com.college.ppp;

public record Payment(int id, double amount, String note, long timestamp) {
    @Override
    public String toString() {
        return "Payment{id=" + id + ", amount=" + amount + ", note='" + note + "'}";
    }
}