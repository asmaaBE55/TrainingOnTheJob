package com.gestioneacquisti.service.impl;


import com.gestioneacquisti.dao.ScontrinoDao;
import com.gestioneacquisti.exception.ScontrinoNotFoundException;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Scontrino;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ScontrinoServiceImpl implements ScontrinoService {
    private final ScontrinoDao scontrinoDao;
    private final AcquistoService acquistoService;
    @Override
    public Scontrino creaScontrinoDaAcquisto(Long acquistoId) {
        Scontrino scontrino=new Scontrino();
        Acquisto acquisto = acquistoService.findById(acquistoId);
        scontrino.setData_scontrino(LocalDateTime.now());
        BigDecimal totale = new BigDecimal(acquisto.getQuantitaAcquistata()).multiply(new BigDecimal(String.valueOf(acquisto.getPrezzoDiAcquisto())));
        scontrino.setNome_prodotto_acquistato(acquisto.getNome_prodotto_acquistato());
        scontrino.setTotale(totale);
        scontrino.addAcquisto(acquisto);
        scontrinoDao.save(scontrino);
        return scontrino;
    }
    @Override
    public Map<Long, List<Scontrino>> raggruppaScontriniPerAcquistoId(List<Scontrino> scontrini) {
        return scontrini.stream()
                .collect(Collectors.groupingBy(scontrino -> scontrino.getAcquisto().getId()));
    }

    @Override
    public List<Scontrino> findAll() {
        return scontrinoDao.findAll();
    }

    @Override
    public Scontrino getScontrinoById(Long id) {
        return scontrinoDao
                .findById(id).orElseThrow(() -> new ScontrinoNotFoundException(id));
    }

}
