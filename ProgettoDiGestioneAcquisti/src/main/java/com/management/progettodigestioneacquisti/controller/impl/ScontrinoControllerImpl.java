package com.management.progettodigestioneacquisti.controller.impl;


import com.management.progettodigestioneacquisti.controller.ScontrinoController;
import com.management.progettodigestioneacquisti.dto.ScontrinoDto;
import com.management.progettodigestioneacquisti.mapper.ScontrinoMapper;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.model.Scontrino;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/scontrini")
@RequiredArgsConstructor
public class ScontrinoControllerImpl implements ScontrinoController {
    private final ScontrinoService scontrinoService;
    private final ScontrinoMapper scontrinoMapper;
    private final ClienteService clienteService;

    @Override
    @PostMapping("/acquisti/{clienteId}")
    public ResponseEntity<String> creaScontrinoDaAcquisto(@PathVariable Long clienteId, @RequestBody Acquisto acquisto) {
        try {
            Cliente cliente = clienteService.getClienteById(clienteId);
            Scontrino scontrino = scontrinoService.getScontrinoById(acquisto.getId());
            scontrinoService.creaScontrinoDaAcquisto(cliente.getId(),acquisto);
            return ResponseEntity.ok("Scontrino creato con successo. Numero scontrino: " + scontrino.getId());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Si è verificato un errore durante la creazione dello scontrino.");
        }
    }


    @Override
    @GetMapping("/{id}")
    public ScontrinoDto getScontrinoById(@PathVariable Long id) {
        Scontrino scontrino = scontrinoService.getScontrinoById(id);
        return scontrinoMapper.asDTO(scontrino);
    }
}