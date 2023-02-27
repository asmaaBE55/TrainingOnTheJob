package com.gestioneacquisti.service;

import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;

public interface AcquistoService {
    void compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata) throws InsufficientFundsException, ProductNotFoundException;
}
