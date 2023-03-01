package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.AcquistoDao;
import com.gestioneacquisti.dao.StoricoAcquistiDao;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.model.StoricoAcquisti;
import com.gestioneacquisti.service.StoricoAcquistiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Transactional
@Service
public class StoricoAcquistiServiceImpl implements StoricoAcquistiService {
    private final AcquistoDao acquistoDao;
    private final StoricoAcquistiDao storicoAcquistiDao;

    @Override
    public void salvaAcquisto(Acquisto acquisto) {
        BigDecimal prezzo_di_acquisto=acquisto.getPrezzoDiAcquisto();
        int quantita_acquistata=acquisto.getQuantitaAcquistata();
        acquisto.setPrezzoDiAcquisto(prezzo_di_acquisto);
        acquisto.setQuantitaAcquistata(quantita_acquistata);
        acquistoDao.save(acquisto);
    }
    @Override
    public void salvaStoricoAcquisti(Cliente cliente, StoricoAcquisti storicoAcquisti, Prodotto prodotto) {
        storicoAcquisti.setAcquisto(storicoAcquisti.getAcquisto());
        storicoAcquisti.setNumeroAcquisti(cliente.getNumeroAcquisti());
        storicoAcquistiDao.save(storicoAcquisti);
    }
}


