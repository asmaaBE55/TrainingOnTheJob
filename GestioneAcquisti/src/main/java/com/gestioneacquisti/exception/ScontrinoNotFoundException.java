package com.gestioneacquisti.exception;

/**
 * Classe che lancia un'eccezzione se l'utente richiesto non è presente nel database
 **/
public class ScontrinoNotFoundException extends RuntimeException {

    public ScontrinoNotFoundException(Long id) {
        super("Scontrino with ID " + id + " not found.");
    }

    public ScontrinoNotFoundException(String id) {
        super("Scontrino with ID " + id + " not found.");
    }
}

