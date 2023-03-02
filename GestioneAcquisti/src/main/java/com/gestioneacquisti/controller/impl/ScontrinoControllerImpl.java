package com.gestioneacquisti.controller.impl;

import com.gestioneacquisti.controller.ScontrinoController;
import com.gestioneacquisti.dto.ScontrinoDto;
import com.gestioneacquisti.mapper.ScontrinoMapper;
import com.gestioneacquisti.model.Scontrino;
import com.gestioneacquisti.service.ScontrinoService;
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

    @PostMapping("/acquisti/{acquistoId}")
    public ResponseEntity<String> creaScontrinoDaAcquisto(@PathVariable Long acquistoId) {
        try {
            Scontrino scontrino = scontrinoService.creaScontrinoDaAcquisto(acquistoId);
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