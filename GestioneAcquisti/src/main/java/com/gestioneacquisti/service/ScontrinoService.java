package com.gestioneacquisti.service;

import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.exception.ProductNotFoundException;

import java.time.LocalDate;

public interface ScontrinoService {

    void creaScontrino(Long clienteId, AcquistoDto acquistoDto) throws ProductNotFoundException;
}
