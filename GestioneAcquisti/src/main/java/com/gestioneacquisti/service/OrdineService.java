package com.gestioneacquisti.service;

import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Ordine;

import java.util.List;

public interface OrdineService {
    Ordine creaOrdine(Cliente cliente, List<Acquisto> acquisti);

    void aggiornaOrdine(Ordine ordine, Ordine.StatoOrdine nuovoStato);
}
