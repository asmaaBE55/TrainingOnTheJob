package com.gestioneacquisti.service;

import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Scontrino;

public interface ScontrinoService {

    Scontrino creaScontrinoDaAcquisto(Long acquistoId) throws InsufficientFundsException, ProductNotFoundException;

    Scontrino getScontrinoById(Long id);
}
