package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ClienteService;
import com.gestioneacquisti.service.ProdottoService;
import com.gestioneacquisti.service.ScontrinoService;
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

    @Override
    public void compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata) throws InsufficientFundsException, ProductNotFoundException {
        BigDecimal prezzoTotale = prodotto.getPrezzo().multiply(new BigDecimal(quantitaDesiderata));
        if (cliente.getBudget().compareTo(prezzoTotale) < 0) {
            throw new InsufficientFundsException("Saldo insufficiente per effettuare l'acquisto.");
        }
        clienteService.aggiornaBudget(cliente, prezzoTotale);
        prodottoService.updateProdottoQuantita(prodotto);
    }
}
