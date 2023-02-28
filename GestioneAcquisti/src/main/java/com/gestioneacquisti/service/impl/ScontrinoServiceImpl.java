package com.gestioneacquisti.service.impl;


import com.gestioneacquisti.dao.ScontrinoDao;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Scontrino;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ScontrinoServiceImpl implements ScontrinoService {
    private final ScontrinoDao scontrinoDao;
    private final AcquistoService acquistoService;
@Override
public Scontrino creaScontrinoDaAcquisto(Long acquistoId) {
    Acquisto acquisto = acquistoService.findById(acquistoId);
    Scontrino scontrino = new Scontrino();
    scontrino.addAcquisto(acquisto);
    scontrinoDao.save(scontrino);
    return scontrino;

    }

}
