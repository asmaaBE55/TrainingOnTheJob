package com.management.progettodigestioneacquisti.input;

import java.util.Random;

public class EanGenerator {

    public static String generateEan() {
        Random rnd = new Random();
        int[] ean = new int[13];
        int sum = 0;

        // Genera i primi 12 cifre casuali
        for (int i = 0; i < 12; i++) {
            ean[i] = rnd.nextInt(10);
            if (i % 2 == 0) {
                sum += ean[i];
            } else {
                sum += ean[i] * 3;
            }
        }

        // Calcola la cifra di controllo
        int check = (10 - (sum % 10)) % 10;
        ean[12] = check;

        // Converte l'array di cifre in una stringa
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            sb.append(ean[i]);
        }
        return sb.toString();
    }

}
