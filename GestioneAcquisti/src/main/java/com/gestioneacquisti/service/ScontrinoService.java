package com.gestioneacquisti.service;

import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Scontrino;

import java.time.LocalDate;

public interface ScontrinoService {
    Scontrino creaScontrinoDaAcquisto(Long acquistoId);
}
