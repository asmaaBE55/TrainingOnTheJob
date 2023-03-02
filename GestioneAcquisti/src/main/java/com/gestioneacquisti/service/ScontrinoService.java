package com.gestioneacquisti.service;

import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Scontrino;

import java.util.List;
import java.util.Map;

public interface ScontrinoService {

    Scontrino creaScontrinoDaAcquisto(Long acquistoId) throws InsufficientFundsException, ProductNotFoundException;

    Scontrino getScontrinoById(Long id);
}
