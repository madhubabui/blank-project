package com.college.ppp;

public record KPI(int id, String name, String value, long timestamp) {
    @Override
    public String toString() {
        return "KPI{id=" + id + ", name='" + name + "', value='" + value + "'}";
    }
}