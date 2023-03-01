package com.gestioneacquisti.service;

import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Scontrino;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ScontrinoService {

    Scontrino creaScontrinoDaAcquisto(Long acquistoId) throws InsufficientFundsException, ProductNotFoundException;

    Map<Long, List<Scontrino>> raggruppaScontriniPerAcquistoId(List<Scontrino> scontrini);

    List<Scontrino> findAll();

    Scontrino getScontrinoById(Long id);
}
