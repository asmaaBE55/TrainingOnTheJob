package com.management.progettodigestioneacquisti.exception;

public class NotEnoughPointsException extends Exception {
    public NotEnoughPointsException(String id) {
        super("Punti insufficienti");
    }
}
