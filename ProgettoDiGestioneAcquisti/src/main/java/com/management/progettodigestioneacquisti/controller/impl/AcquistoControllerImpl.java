package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.AcquistoController;
import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.FidelityCardNotFoundException;
import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
import com.management.progettodigestioneacquisti.exception.NotEnoughPointsException;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.FidelityCard;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/acquisti")
@RequiredArgsConstructor
public class AcquistoControllerImpl implements AcquistoController {
    private final AcquistoService acquistoService;
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;
    private final StoricoAcquistiService storicoAcquistiService;
    private final FidelityCardService fidelityCardService;

    /**
     * Questo controller permette al cliente di acquistare uno o più prodotti sfruttando del metodo compraProdotto() del servizio
     * AcquistoService che si occupa di creare un nuovo oggetto Acquisto,
     * aggiornare le quantità dei prodotti acquistati nel database e creare un nuovo oggetto StoricoAcquisti.
     * Infine, l'oggetto Acquisto appena creato viene aggiunto a una lista di acquisti,
     * che alla fine del ciclo vengono restituiti come risultato della chiamata.
     **/

    @Override
    @PostMapping("/{id}/acquista-prodotti")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> compraProdotti(@PathVariable Long id, @RequestBody List<ProdottoDto> prodotti, BindingResult result) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            List<Acquisto> acquisti = new ArrayList<>();
            for (ProdottoDto prodottoDto : prodotti) {
                Prodotto prodotto = prodottoService.getProdottoById(prodottoDto.getEanProdotto());
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

    @Override
    @PostMapping("/{id}/compra-prodotto-fidelitycard/{card_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> compraProdottoConFidelityCard(@PathVariable Long id, @PathVariable Long card_id, @RequestBody List<ProdottoDto> prodotti, BindingResult result) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            List<Acquisto> acquisti = new ArrayList<>();
            // Verifica se il cliente ha una fidelity card
            FidelityCard fidelityCard = fidelityCardService.getFidelityCardByClienteId(cliente.getId());
            if (fidelityCard == null) {
                throw new FidelityCardNotFoundException("Devi avere la fidelity card per poter acquistare prodotti scontati");
            }
            for (ProdottoDto prodottoDto : prodotti) {
                Prodotto prodotto = prodottoService.getProdottoById(prodottoDto.getEanProdotto());
                int quantitaDesiderata = prodottoDto.getQuantitaDisponibile();

                if (prodotto.isStatoSconto()) {
                    Acquisto acquisto = acquistoService.compraProdottoConFidelityCard(cliente, prodotto, quantitaDesiderata, result);
                    acquisti.add(acquisto);
                } else {
                    Acquisto acquisto = acquistoService.compraProdotto(cliente, prodotto, quantitaDesiderata, result);
                    acquisti.add(acquisto);
                }
                if (fidelityCard.getPuntiAccumulati() >= 100) {
                    Acquisto acquisto = acquistoService.prodottoRegalo100PuntiAccumulati(cliente);
                    acquisti.add(acquisto);
                }
            }
            return ResponseEntity.ok(acquisti);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InsufficientFundsException | FidelityCardNotFoundException | NotEnoughPointsException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @GetMapping("/acquisti/cliente/{clienteId}")
    public ResponseEntity<String> getAcquistiByClienteIdAndPeriodoAsString(@PathVariable Long clienteId,
                                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
                                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine) {
        String acquistiAsString = storicoAcquistiService.getAcquistiByClienteIdAndPeriodoAsString(clienteId, dataInizio, dataFine);
        return ResponseEntity.ok(acquistiAsString);
    }

    @Override
    @GetMapping("/profitto")
    public ResponseEntity<Resource> generaReportProfitto() {
        try {
            // Genera il report e ottiene il file
            acquistoService.generaReportProfitto();
            Resource file = new UrlResource("file:report_profitto.xlsx");

            // Prepara la response con il file allegato
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_profitto.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .body(file);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


