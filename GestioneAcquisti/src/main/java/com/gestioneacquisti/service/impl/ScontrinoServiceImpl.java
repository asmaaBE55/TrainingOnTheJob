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

@RequiredArgsConstructor
@Transactional
@Service
public class ScontrinoServiceImpl implements ScontrinoService {
    private final ScontrinoDao scontrinoDao;
    private final AcquistoService acquistoService;

    @Override
    public Scontrino creaScontrinoDaAcquisto(Long acquistoId) {

        BigDecimal totale = BigDecimal.ZERO;//inizializza il totale a zero

        Scontrino scontrino = new Scontrino();//creazione nuovo scontrino
        Acquisto acquisto = acquistoService.findById(acquistoId);//prendere id acquisto che va messo dentro lo scontrino
        scontrino.setData_scontrino(LocalDateTime.now());//mette la data e il tempo corrente

        int quantita_acquistata = acquisto.getQuantitaAcquistata();//serve a prendere la quantita acquistata

        BigDecimal prezzoAcquisto = acquisto.getPrezzo().divide(BigDecimal.valueOf(acquisto.getQuantitaAcquistata()));//serve a prendere il prezzo di acquisto
        scontrino.setPrezzo_acquisto(prezzoAcquisto);
        totale = (prezzoAcquisto.multiply(BigDecimal.valueOf(quantita_acquistata)));//calcola il totale

        scontrino.setNome_prodotto_acquistato(acquisto.getNome_prodotto_acquistato());//prende il nome prodotto acquistato
        scontrino.setTotale(totale);//inserisce il totale nel scontrino

        scontrino.addAcquisto(acquisto);
        scontrinoDao.save(scontrino);//salva scontrino
        return scontrino;
    }

    @Override
    public Scontrino getScontrinoById(Long id) {
        return scontrinoDao
                .findById(id).orElseThrow(() -> new ScontrinoNotFoundException(id));
    }

}
