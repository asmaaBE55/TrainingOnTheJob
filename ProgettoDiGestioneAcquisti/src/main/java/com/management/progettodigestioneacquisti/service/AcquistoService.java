package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class AcquistoService {

    private final AcquistoRepository acquistoRepository;
    private final StoricoAcquistiService storicoAcquistiService;
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;

    public void compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException("Dati non validi" + result);
        }
        BigDecimal prezzoTotale = prodotto.getPrezzoUnitario().multiply(new BigDecimal(quantitaDesiderata));
        Acquisto acquisto = new Acquisto();
        acquisto.setCliente(cliente);
        acquisto.setQuantitaAcquistata(quantitaDesiderata);
        acquisto.setNomeProdottoAcquistato(prodotto.getNome());
        acquisto.setPrezzoDiAcquisto(prezzoTotale);

        StoricoAcquisti storicoAcquisti = new StoricoAcquisti();
        storicoAcquisti.setAcquisti(storicoAcquisti.getAcquisti());
        storicoAcquisti.setNumeroAcquisti(storicoAcquisti.getNumeroAcquisti());
        storicoAcquisti.setNomeProdotto(storicoAcquisti.getNomeProdotto());
        storicoAcquisti.setId(acquisto.getId());

        acquistoRepository.save(acquisto);
        prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
        clienteService.updateNumeroAcquisti(cliente, acquisto);
        clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
        clienteService.updateImportoTotaleSpeso(cliente, prezzoTotale);
        clienteService.aggiornaBudget(cliente, prezzoTotale);
        storicoAcquistiService.saveAcquisto(storicoAcquisti);

    }

    public Acquisto findById(Long id) {
        return acquistoRepository.findAcquistoById(id);
    }
    public List<Acquisto> compraProdotti(Cliente cliente, List<ProdottoDto> prodotti) throws ProductNotFoundException {
        List<Acquisto> acquisti = new ArrayList<>();
        BigDecimal totale = BigDecimal.ZERO;
        BigDecimal prezzoTotale = null;
        for (ProdottoDto prodottoQuantita : prodotti) {
            Prodotto prodotto = prodottoService.getProdottoById(prodottoQuantita.getId());
            int quantitaDesiderata = prodottoQuantita.getQuantitaDisponibile();

            prezzoTotale = prodotto.getPrezzoUnitario().multiply(BigDecimal.valueOf(quantitaDesiderata));
            totale = totale.add(prezzoTotale);

            Acquisto acquisto = new Acquisto();
            acquisto.setCliente(cliente);
            acquisto.setQuantitaAcquistata(quantitaDesiderata);
            acquisto.setNomeProdottoAcquistato(prodotto.getNome());
            acquisto.setPrezzoDiAcquisto(prezzoTotale);

            prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
            clienteService.updateNumeroAcquisti(cliente, acquisto);
            clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
            clienteService.updateImportoTotaleSpeso(cliente, prezzoTotale);
            clienteService.aggiornaBudget(cliente, prezzoTotale);

            acquistoRepository.save(acquisto);
            acquisti.add(acquisto);
        }

        clienteService.aggiornaBudget(cliente, totale);
        List<StoricoAcquisti> storicoAcquistiList = acquisti.stream().map(acquistoDTO -> {
            StoricoAcquisti storicoAcquisti = new StoricoAcquisti();
            storicoAcquisti.setId(storicoAcquisti.getId());
            storicoAcquisti.setAcquisti(storicoAcquisti.getAcquisti());
            storicoAcquisti.setNumeroAcquisti(acquistoDTO.getQuantitaAcquistata());
            storicoAcquisti.setNomeProdotto(acquistoDTO.getNomeProdottoAcquistato());
            return storicoAcquisti;

        }).collect(Collectors.toList());

        storicoAcquistiService.saveAllAcquisti(storicoAcquistiList);
        return acquisti;
    }

}
