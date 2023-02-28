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
    public void salvaAcquisto(Acquisto acquisto, Prodotto prodotto) {
        BigDecimal prezzo_di_acquisto=prodotto.getPrezzo();
        int quantita_acquistata=acquisto.getQuantitaAcquistata();
        acquisto.setPrezzoDiAcquisto(prezzo_di_acquisto);
        acquisto.setQuantitaAcquistata(quantita_acquistata);
        acquistoDao.save(acquisto);
    }
    @Override
    public void salvaStoricoAcquisti(Cliente cliente,StoricoAcquisti storicoAcquisti, Prodotto prodotto) {
        int numero_acquisti=cliente.getNumeroAcquisti();
        String nome_prodotto=prodotto.getNome();
        storicoAcquisti.setNome_prodotto(nome_prodotto);
        storicoAcquisti.setNumeroAcquisti(numero_acquisti);
        storicoAcquisti.setCliente(cliente);
        storicoAcquistiDao.save(storicoAcquisti);
    }
}
