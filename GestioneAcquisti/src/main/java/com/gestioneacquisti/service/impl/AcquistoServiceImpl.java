package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.AcquistoDao;
import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.dto.ProdottoDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

        Acquisto acquisto = new Acquisto();
        acquisto.setCliente(cliente);
        acquisto.setQuantitaAcquistata(quantitaDesiderata);
        acquisto.setNome_prodotto_acquistato(prodotto.getNome());
        acquisto.setPrezzo(prezzoTotale);

        StoricoAcquisti storicoAcquisti = new StoricoAcquisti();
        storicoAcquisti.setAcquisto(acquisto);
        storicoAcquisti.setNumeroAcquisti(acquisto.getQuantitaAcquistata());
        storicoAcquisti.setNome_prodotto(acquisto.getNome_prodotto_acquistato());
        storicoAcquisti.setCliente(cliente);


        acquistoDao.save(acquisto);
        storicoAcquistiService.save(storicoAcquisti);
    }

    @Override
    public List<AcquistoDto> compraProdotti(Cliente cliente, List<ProdottoDto> prodotti) throws InsufficientFundsException, ProductNotFoundException {
        List<AcquistoDto> acquisti = new ArrayList<>();
        BigDecimal totale = BigDecimal.ZERO;

        if (cliente.getBudget().compareTo(totale) < 0) {
            throw new InsufficientFundsException("Saldo insufficiente per effettuare l'acquisto.");
        }

        for (ProdottoDto prodottoQuantita : prodotti) {
            Prodotto prodotto = prodottoService.findProductById(prodottoQuantita.getId());
            int quantitaDesiderata = prodottoQuantita.getQuantita();

            BigDecimal prezzoTotale = prodotto.getPrezzo().multiply(BigDecimal.valueOf(quantitaDesiderata));
            totale = totale.add(prezzoTotale);

            Acquisto acquisto = new Acquisto();
            acquisto.setCliente(cliente);
            acquisto.setQuantitaAcquistata(quantitaDesiderata);
            acquisto.setNome_prodotto_acquistato(prodotto.getNome());
            acquisto.setPrezzo(prezzoTotale);

            acquistoDao.save(acquisto);
            acquisti.add(new AcquistoDto());
        }

        clienteService.aggiornaBudget(cliente, totale);

        List<StoricoAcquisti> storicoAcquistiList = acquisti.stream().map(acquistoDTO -> {
            StoricoAcquisti storicoAcquisti = new StoricoAcquisti();
            storicoAcquisti.setAcquisto(acquistoDTO.getStoricoAcquisti().getAcquisto());
            storicoAcquisti.setNumeroAcquisti(acquistoDTO.getQuantitaAcquistata());
            storicoAcquisti.setNome_prodotto(acquistoDTO.getNome_prodotto_acquistato());
            storicoAcquisti.setCliente(cliente);
            return storicoAcquisti;
        }).collect(Collectors.toList());

        storicoAcquistiService.saveAll(storicoAcquistiList);

        return acquisti;
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

    @Override
    public List<Acquisto> findAll() {
        return acquistoDao.findAll();
    }
}
