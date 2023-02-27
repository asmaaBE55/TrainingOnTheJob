package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.AcquistoDao;
import com.gestioneacquisti.dao.OrdineDao;
import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Ordine;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ClienteService;
import com.gestioneacquisti.service.OrdineService;
import com.gestioneacquisti.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Transactional
@Service
public class AcquistoServiceImpl implements AcquistoService {
    @Autowired
    private final OrdineDao ordineDao;
    @Autowired
    private final AcquistoDao acquistoDao;
    @Autowired
    private final ClienteService clienteService;
    @Autowired
    private final ProdottoService prodottoService;
    @Autowired
    private final OrdineService ordineService;

@Override
public void compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata) throws InsufficientFundsException, ProductNotFoundException {
    List<Acquisto>acquistiCorrenti=new ArrayList<>();
    if (prodotto.getQuantita() < quantitaDesiderata) {
        throw new ProductNotFoundException("Quantità di prodotto non disponibile.");
    }

    BigDecimal prezzoTotale = prodotto.getPrezzo().multiply(new BigDecimal(quantitaDesiderata));

    if (cliente.getBudget().compareTo(prezzoTotale) < 0) {
        throw new InsufficientFundsException("Saldo insufficiente per effettuare l'acquisto.");
    }

    clienteService.aggiornaBudget(cliente,cliente.getImportoTotaleSpeso());

    Ordine ordine = ordineService.creaOrdine(cliente, acquistiCorrenti);
    Acquisto acquisto = Acquisto.builder()
            .prodotto(prodotto)
            .prezzoDiAcquisto(prodotto.getPrezzo())
            .quantita(quantitaDesiderata)
            .ordine(ordine)
            .build();
    acquistiCorrenti.add(acquisto);

    ordineDao.save(ordine);
    ordineService.aggiornaOrdine(ordine, Ordine.StatoOrdine.IN_CORSO);

    prodotto.setQuantita(quantitaDesiderata);
    prodottoService.createProduct(prodotto);
}


}
