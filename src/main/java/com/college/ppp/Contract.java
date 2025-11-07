package com.college.ppp;

public record Contract(int id, String terms, double value) {
    @Override
    public String toString() {
        return "Contract{id=" + id + ", value=" + value + "}";
    }
}