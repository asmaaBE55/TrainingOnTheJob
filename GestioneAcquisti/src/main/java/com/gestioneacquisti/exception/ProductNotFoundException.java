package com.gestioneacquisti.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(Long id) {
        super("Product with ID " + id + " not found.");
    }

    public ProductNotFoundException(String id) {
        super("User with ID " + id + " not found.");
    }

}
