package com.hayden.portfolio;

public class HoldingNotFoundException extends RuntimeException {
    public HoldingNotFoundException(Long id) {
        super("No holding with id " + id);
    }
}