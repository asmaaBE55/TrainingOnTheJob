package com.management.progettodigestioneacquisti.exception;

/**
 * Classe che lancia un'eccezzione se l'utente richiesto non è presente nel database
 **/
public class AcquistoNotFoundException extends RuntimeException {

    public AcquistoNotFoundException(Long id) {
        super("Acquisto with ID " + id + " not found.");
    }

    public AcquistoNotFoundException(String id) {
        super("Acquisto with ID " + id + " not found.");
    }
}

