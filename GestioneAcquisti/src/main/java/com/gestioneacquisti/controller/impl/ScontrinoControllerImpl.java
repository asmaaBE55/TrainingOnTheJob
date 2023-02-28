package com.gestioneacquisti.controller.impl;

import com.gestioneacquisti.controller.ScontrinoController;
import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Scontrino;
import com.gestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/scontrini")
@RequiredArgsConstructor
public class ScontrinoControllerImpl implements ScontrinoController {
    private final ScontrinoService scontrinoService;

    @PostMapping("/{clienteId}")
    public ResponseEntity<String> creaScontrino(@PathVariable Long clienteId, @RequestBody AcquistoDto acquistoDto) {
        try {
            scontrinoService.creaScontrino(clienteId,acquistoDto);
            return ResponseEntity.ok("Scontrino creato con successo.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}