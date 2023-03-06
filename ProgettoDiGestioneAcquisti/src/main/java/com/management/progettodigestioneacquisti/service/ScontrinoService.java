package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Scontrino;
import com.management.progettodigestioneacquisti.repository.ScontrinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class ScontrinoService {

    private final ScontrinoRepository scontrinoRepository;
    private final ClienteService clienteService;

    public Scontrino creaScontrinoDaAcquisto(Long clienteId, Acquisto acquisto) {

        BigDecimal totale = BigDecimal.ZERO;//inizializza il totale a zero

        Scontrino scontrino = new Scontrino();//creazione nuovo scontrino
        Cliente cliente = clienteService.getClienteById(clienteId);//prendere id acquisto che va messo dentro lo scontrino
        scontrino.setDataScontrino(LocalDateTime.now());//mette la data e il tempo corrente

        int quantita_acquistata = acquisto.getQuantitaAcquistata();//serve a prendere la quantita acquistata

        BigDecimal prezzoAcquisto = acquisto.getPrezzoDiAcquisto().divide(BigDecimal.valueOf(acquisto.getQuantitaAcquistata()));//serve a prendere il prezzo di acquisto
        scontrino.setPrezzoDiAcquisto(prezzoAcquisto);
        totale = (prezzoAcquisto.multiply(BigDecimal.valueOf(quantita_acquistata)));//calcola il totale

        scontrino.setNomeProdottoAcquistato(acquisto.getNomeProdottoAcquistato());//prende il nome prodotto acquistato
        scontrino.setTotale(totale);//inserisce il totale nel scontrino

        scontrinoRepository.save(scontrino);//salva scontrino
        return scontrino;
    }

    public Scontrino getScontrinoById(Long id) {
        return scontrinoRepository.findScontrinoById(id);
    }
}
