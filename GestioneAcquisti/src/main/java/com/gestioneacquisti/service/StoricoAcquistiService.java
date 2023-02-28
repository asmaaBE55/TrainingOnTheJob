package com.gestioneacquisti.service;

import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.model.StoricoAcquisti;

public interface StoricoAcquistiService {
    void salvaAcquisto(Acquisto acquisto, Prodotto prodotto);
    void salvaStoricoAcquisti(Cliente cliente, StoricoAcquisti storicoAcquisti, Prodotto prodotto);
}
