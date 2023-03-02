package com.gestioneacquisti.controller.impl;

import com.gestioneacquisti.controller.AcquistoController;
import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.mapper.AcquistoMapper;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.service.AcquistoService;
import com.gestioneacquisti.service.ClienteService;
import com.gestioneacquisti.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/acquisti")
@RequiredArgsConstructor
public class AcquistoControllerImpl implements AcquistoController {

    private final AcquistoService acquistoService;
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;

    @Override
    @PostMapping("/users/{clienteId}/prodotti/{prodottoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> compraProdotto(@PathVariable Long clienteId, @PathVariable Long prodottoId, @RequestParam int quantitaDesiderata) {
        try {
            Cliente cliente = clienteService.getCliente(clienteId);
            Prodotto prodotto = prodottoService.findProductById(prodottoId);
            acquistoService.compraProdotto(cliente, prodotto,quantitaDesiderata);
            clienteService.updateImportoTotaleSpeso(cliente, prodotto.getPrezzo().multiply(BigDecimal.valueOf(quantitaDesiderata)));
            clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
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
