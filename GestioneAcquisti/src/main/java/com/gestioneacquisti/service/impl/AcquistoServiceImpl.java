package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.AcquistoDao;
import com.gestioneacquisti.exception.AcquistoNotFoundException;
import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.model.StoricoAcquisti;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ClienteService;
import com.gestioneacquisti.service.ProdottoService;
import com.gestioneacquisti.service.StoricoAcquistiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@RequiredArgsConstructor
@Transactional
@Service
public class AcquistoServiceImpl implements AcquistoService {
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;
    private final AcquistoDao acquistoDao;
    private final StoricoAcquistiService storicoAcquistiService;

    @Override
    public void compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata) throws InsufficientFundsException, ProductNotFoundException {
        BigDecimal prezzoTotale = prodotto.getPrezzo().multiply(BigDecimal.valueOf(quantitaDesiderata));

        if (cliente.getBudget().compareTo(prezzoTotale) < 0) {
            throw new InsufficientFundsException("Saldo insufficiente per effettuare l'acquisto.");
        }

        clienteService.aggiornaBudget(cliente, prezzoTotale);
        prodottoService.updateProdottoQuantita(prodotto);

        Acquisto acquisto=new Acquisto();
        acquisto.setCliente(cliente);
        acquisto.setQuantitaAcquistata(quantitaDesiderata);
        acquisto.setNome_prodotto_acquistato(prodotto.getNome());
        acquisto.setPrezzoDiAcquisto(prezzoTotale);

        StoricoAcquisti storicoAcquisti = new StoricoAcquisti();
        storicoAcquisti.setAcquisto(acquisto);
        storicoAcquisti.setNumeroAcquisti(acquisto.getQuantitaAcquistata());
        storicoAcquisti.setNome_prodotto(acquisto.getNome_prodotto_acquistato());
        storicoAcquisti.setCliente(cliente);


        acquistoDao.save(acquisto);
        storicoAcquistiService.save(storicoAcquisti);
    }


    @Override
    public Acquisto findById(Long id) {
        return acquistoDao.findById(id)
                .orElseThrow(() -> new AcquistoNotFoundException(id));

    }

    @Override
    public Acquisto save(Acquisto acquisto) {
        return acquistoDao.save(acquisto);
    }
}
