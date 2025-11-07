package com.college.ppp;

public record Partner(int id, String name, String email) {
    @Override
    public String toString() {
        return "Partner{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}