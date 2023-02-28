package com.gestioneacquisti.service.impl;


import com.gestioneacquisti.dao.ScontrinoDao;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Scontrino;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class ScontrinoServiceImpl implements ScontrinoService {
    private final ScontrinoDao scontrinoDao;
    private final AcquistoService acquistoService;
@Override
public Scontrino creaScontrinoDaAcquisto(Long acquistoId, Cliente cliente, Scontrino scontrino) {
    Acquisto acquisto = acquistoService.findById(acquistoId);
    scontrino.setData_scontrino(LocalDateTime.now());
    BigDecimal totale=new BigDecimal(0).add(cliente.getImportoTotaleSpeso());
    scontrino.setTotale(totale);
    scontrino.addAcquisto(acquisto);
    scontrinoDao.save(scontrino);
    return scontrino;
    }

}
