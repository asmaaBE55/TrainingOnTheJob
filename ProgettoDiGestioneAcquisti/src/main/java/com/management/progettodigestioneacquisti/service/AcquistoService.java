package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
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
import java.time.LocalDate;


@RequiredArgsConstructor
@Transactional
@Service
public class AcquistoService {

    private final AcquistoRepository acquistoRepository;
    private final StoricoAcquistiService storicoAcquistiService;
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;

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

}
