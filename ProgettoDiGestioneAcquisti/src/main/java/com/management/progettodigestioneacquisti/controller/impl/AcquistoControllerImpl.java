package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.AcquistoController;
import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.service.AcquistoService;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import com.management.progettodigestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/acquisti")
@RequiredArgsConstructor
public class AcquistoControllerImpl implements AcquistoController {
    private final AcquistoService acquistoService;
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;
    private final ScontrinoService scontrinoService;

    @Override
    @PostMapping("/clienti/{clienteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> compraProdotto(@PathVariable Long clienteId, @RequestBody @Valid ProdottoDto dtoProdotti, BindingResult result, @RequestParam(value = "quantita", defaultValue = "1") int quantitaDesiderata) throws InsufficientFundsException, ProductNotFoundException {
        Cliente cliente = clienteService.getClienteById(clienteId);
        Prodotto prodotto = prodottoService.getProdottoById(dtoProdotti.getId());
        acquistoService.compraProdotto(cliente, prodotto, quantitaDesiderata, result);
        return ResponseEntity.ok("Prodotto acquistato con successo.");
    }

    @Override
    @PostMapping("/{id}/acquista-prodotti")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> compraProdotti(@PathVariable Long id, @RequestBody List<ProdottoDto> prodotti, BindingResult result) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            List<Acquisto> acquisti = new ArrayList<>();
            for (ProdottoDto prodottoDto : prodotti) {
                Prodotto prodotto = prodottoService.getProdottoById(prodottoDto.getId());
                int quantitaDesiderata = prodottoDto.getQuantitaDisponibile();
                Acquisto acquisto = acquistoService.compraProdotto(cliente, prodotto, quantitaDesiderata, result);
                acquisti.add(acquisto);
            }
            return ResponseEntity.ok(acquisti);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }
    }
/**
 Questo controller permette al cliente di acquistare uno o più prodotti sfruttando del metodo compraProdotto() del servizio
 AcquistoService che si occupa di creare un nuovo oggetto Acquisto,
 aggiornare le quantità dei prodotti acquistati nel database e creare un nuovo oggetto StoricoAcquisti.
 Infine, l'oggetto Acquisto appena creato viene aggiunto a una lista di acquisti,
 che alla fine del ciclo vengono restituiti come risultato della chiamata.
 **/
}
