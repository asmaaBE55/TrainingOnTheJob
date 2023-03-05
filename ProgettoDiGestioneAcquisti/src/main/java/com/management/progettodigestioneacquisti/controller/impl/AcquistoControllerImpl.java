package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.AcquistoController;
import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.service.AcquistoService;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/acquisti")
@RequiredArgsConstructor
public class AcquistoControllerImpl implements AcquistoController {
    private final AcquistoService acquistoService;
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;


    @Override
    @PostMapping("/users/{clienteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> compraProdotto(@PathVariable Long clienteId, @RequestBody @Valid ProdottoDto dtoProdotti, BindingResult result, @RequestParam(value = "quantita", defaultValue = "1") int quantitaDesiderata) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        Prodotto prodotto = prodottoService.getProdottoById(dtoProdotti.getId());
        acquistoService.compraProdotto(cliente, prodotto, quantitaDesiderata, result);
        return ResponseEntity.ok("Prodotto acquistato con successo.");
    }
    @Override
    @PostMapping("/{id}/acquista-prodotti")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> compraProdotti(@PathVariable Long id, @RequestBody List<ProdottoDto> prodotti) {

        try {
            Cliente cliente = clienteService.getClienteById(id);
            List<Acquisto> acquisti = acquistoService.compraProdotti(cliente,prodotti);
            return ResponseEntity.ok(acquisti);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
