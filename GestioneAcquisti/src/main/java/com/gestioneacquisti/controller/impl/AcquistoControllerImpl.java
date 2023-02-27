package com.gestioneacquisti.controller.impl;

import com.gestioneacquisti.controller.AcquistoController;
import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.mapper.AcquistoMapper;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Ordine;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ClienteService;
import com.gestioneacquisti.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/acquisti")
@RequiredArgsConstructor
public class AcquistoControllerImpl implements AcquistoController {
    @Autowired
    private final AcquistoService acquistoService;
    @Autowired
    private final ClienteService clienteService;
    @Autowired
    private final ProdottoService prodottoService;

    @Override
    @PostMapping("/users/{clienteId}/prodotti/{prodottoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> compraProdotto(@PathVariable Long clienteId, @PathVariable Long prodottoId) {
        try {
            Cliente cliente = clienteService.getCliente(clienteId);
            Prodotto prodotto = prodottoService.findById(prodottoId);
            acquistoService.compraProdotto(cliente, prodotto,prodotto.getQuantita());
            Ordine ordine = new Ordine();
            ordine.setCliente(cliente);
            ordine.setData(LocalDate.now());
            ordine.setTotale(prodotto.getPrezzo().doubleValue());
            ordine.getAcquisti().add(new Acquisto());
            clienteService.updateImportoTotaleSpeso(cliente,cliente.getImportoTotaleSpeso());
            clienteService.updateClientStatus(cliente,cliente.getImportoTotaleSpeso());
            prodottoService.updateProdottoQuantita(prodotto);
            clienteService.updateNumeroAcquisti(cliente);
            return ResponseEntity.ok("Prodotto acquistato con successo.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(e.getMessage());
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
