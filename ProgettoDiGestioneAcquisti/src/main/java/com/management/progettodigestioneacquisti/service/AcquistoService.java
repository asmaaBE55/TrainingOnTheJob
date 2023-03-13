package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
import com.management.progettodigestioneacquisti.exception.NotEnoughPointsException;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.*;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;
import com.management.progettodigestioneacquisti.repository.FidelityCardRepository;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;


@RequiredArgsConstructor
@Transactional
@Service
public class AcquistoService {

    private final AcquistoRepository acquistoRepository;
    private final StoricoAcquistiService storicoAcquistiService;
    private final ClienteService clienteService;
    private final FidelityCardService fidelityCardService;
    private final FidelityCardRepository fidelityCardRepository;
    private final ProdottoService prodottoService;
    private final ClienteRepository clienteRepository;
    private final ProdottoRepository prodottoRepository;
    public Acquisto compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata, BindingResult result) throws ProductNotFoundException, InsufficientFundsException {
        if (result.hasErrors()) {
            throw new ValidationException("Dati non validi" + result);
        }
        BigDecimal prezzoTotale = prodotto.getPrezzoUnitario().multiply(new BigDecimal(quantitaDesiderata));

        if (prezzoTotale.compareTo(cliente.getBudget()) > 0) {
            throw new InsufficientFundsException("Budget insufficiente");
        }

        if (quantitaDesiderata > prodotto.getQuantitaDisponibile()) {
            throw new ProductNotFoundException("Quantità esaurita");
        }
        Acquisto acquisto = new Acquisto();
        acquisto.setCliente(cliente);
        acquisto.setQuantitaAcquistata(quantitaDesiderata);
        acquisto.setNomeProdottoAcquistato(prodotto.getNome());
        acquisto.setPrezzoDiAcquisto(prezzoTotale);
        acquisto.setDataAcquisto(LocalDate.now());
        acquisto.setScontrino(acquisto.getScontrino());

        // Cerca il prodotto nello storico degli acquisti del cliente
        StoricoAcquisti storicoAcquisti = cliente.getStoricoAcquisti().stream()
                .filter(sa -> sa.getProdotto().equals(prodotto))
                .findFirst()
                .orElse(null);

        // Aggiorna la quantità acquistata del prodotto nello storico, o crea una nuova riga nello storico se non esiste ancora
        if (storicoAcquisti != null) {
            storicoAcquisti.setQuantitaAcquistata(storicoAcquisti.getQuantitaAcquistata() + quantitaDesiderata);
        } else {
            storicoAcquisti = new StoricoAcquisti();
            storicoAcquisti.setProdotto(prodotto);
            storicoAcquisti.setCliente(cliente);
            storicoAcquisti.setNomeProdotto(prodotto.getNome());
            storicoAcquisti.setDataAcquisto(LocalDate.now());
            storicoAcquisti.setQuantitaAcquistata(quantitaDesiderata);
        }

        acquistoRepository.save(acquisto);


        prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
        clienteService.updateNumeroAcquisti(cliente, acquisto);
        clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
        clienteService.updateImportoTotaleSpeso(cliente, prezzoTotale);
        clienteService.aggiornaBudget(cliente, prezzoTotale);

        // Aggiorna l'entità StoricoAcquisti con la quantità acquistata aggiornata
        storicoAcquistiService.saveAcquisto(storicoAcquisti);
        return acquisto;

    }
    public Acquisto compraProdottoConFidelityCard(Cliente cliente, Prodotto prodotto, int quantitaDesiderata, BindingResult result) throws ProductNotFoundException, InsufficientFundsException, NotEnoughPointsException {
    if (result.hasErrors()) {
        throw new ValidationException("Dati non validi" + result);
    }
    BigDecimal prezzoTotaleNormale = prodotto.getPrezzoUnitario().multiply(new BigDecimal(quantitaDesiderata));

    if (prezzoTotaleNormale.compareTo(cliente.getBudget()) > 0) {
        throw new InsufficientFundsException("Budget insufficiente");
    }

    if (quantitaDesiderata > prodotto.getQuantitaDisponibile()) {
        throw new ProductNotFoundException("Quantità esaurita");
    }

    BigDecimal prezzoTotale;
    if (prodotto.isStatoSconto()) {
        prezzoTotale = prodotto.getPrezzoScontato().multiply(new BigDecimal(quantitaDesiderata));
    } else {
        prezzoTotale = prezzoTotaleNormale;
    }

    Acquisto acquisto = new Acquisto();
    acquisto.setCliente(cliente);
    acquisto.setQuantitaAcquistata(quantitaDesiderata);
    acquisto.setNomeProdottoAcquistato(prodotto.getNome());
    acquisto.setPrezzoDiAcquisto(prezzoTotale);
    acquisto.setDataAcquisto(LocalDate.now());
    acquisto.setScontrino(acquisto.getScontrino());

    prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
    clienteService.updateNumeroAcquisti(cliente, acquisto);
    clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
    clienteService.updateImportoTotaleSpeso(cliente, prezzoTotale);
    clienteService.aggiornaBudget(cliente, prezzoTotale);
    FidelityCard fidelityCard=fidelityCardRepository.findByClienteId(cliente.getId());

    if (fidelityCard != null) {
        // Aggiorna punti accumulati
        fidelityCardService.aggiornaPunti(fidelityCard, prezzoTotale);
    }
    // Cerca il prodotto nello storico degli acquisti del cliente
    StoricoAcquisti storicoAcquisti = cliente.getStoricoAcquisti().stream()
            .filter(sa -> sa.getProdotto().equals(prodotto))
            .findFirst()
            .orElse(null);

    // Aggiorna la quantità acquistata del prodotto nello storico, o crea una nuova riga nello storico se non esiste ancora
    if (storicoAcquisti != null) {
        storicoAcquisti.setQuantitaAcquistata(storicoAcquisti.getQuantitaAcquistata() + quantitaDesiderata);
    } else {
        storicoAcquisti = new StoricoAcquisti();
        storicoAcquisti.setProdotto(prodotto);
        storicoAcquisti.setCliente(cliente);
        storicoAcquisti.setNomeProdotto(prodotto.getNome());
        storicoAcquisti.setDataAcquisto(LocalDate.now());
        storicoAcquisti.setQuantitaAcquistata(quantitaDesiderata);
    }

    // Aggiorna l'entità StoricoAcquisti con la quantità acquistata aggiornata
    storicoAcquistiService.saveAcquisto(storicoAcquisti);
    acquistoRepository.save(acquisto);

    return acquisto;
  }
    public Acquisto prodottoRegalo100PuntiAccumulati(Cliente cliente) throws NotEnoughPointsException, ProductNotFoundException, InsufficientFundsException {
        FidelityCard fidelityCard = fidelityCardService.getFidelityCardByClienteId(cliente.getId());
        if (fidelityCard != null && fidelityCard.getPuntiAccumulati() >= 100) {
            // Cerca un prodotto scontato
            Prodotto prodotto = prodottoRepository.findByStatoSconto(true).stream().findFirst().orElse(null);
            if (prodotto != null) {
                // Acquista il prodotto con prezzo a zero
                Acquisto acquisto = new Acquisto();
                acquisto.setCliente(cliente);
                acquisto.setQuantitaAcquistata(1);
                acquisto.setNomeProdottoAcquistato(prodotto.getNome());
                acquisto.setPrezzoDiAcquisto(BigDecimal.ZERO);
                acquisto.setDataAcquisto(LocalDate.now());
                acquisto.setScontrino(acquisto.getScontrino());
                prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
                clienteService.updateNumeroAcquisti(cliente, acquisto);
                clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
                clienteService.updateImportoTotaleSpeso(cliente, acquisto.getPrezzoDiAcquisto());
                clienteService.aggiornaBudget(cliente, acquisto.getPrezzoDiAcquisto());
                fidelityCard.setPuntiAccumulati(fidelityCard.getPuntiAccumulati()-100);
                acquistoRepository.save(acquisto);
                return acquisto;
            }
        }
        throw new NotEnoughPointsException("Non hai abbastanza punti accumulati per avere il prodotto gratuito");
    }

}
