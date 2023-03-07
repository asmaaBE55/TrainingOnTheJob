package com.management.progettodigestioneacquisti.controller.impl;


import com.management.progettodigestioneacquisti.controller.ScontrinoController;
import com.management.progettodigestioneacquisti.dto.ScontrinoDto;
import com.management.progettodigestioneacquisti.exception.UserNotFoundException;
import com.management.progettodigestioneacquisti.mapper.ScontrinoMapper;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Scontrino;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scontrini")
@RequiredArgsConstructor
public class ScontrinoControllerImpl implements ScontrinoController {
    private final ScontrinoService scontrinoService;
    private final ScontrinoMapper scontrinoMapper;
    private final ClienteService clienteService;
    private final AcquistoRepository acquistoRepository;
    @Override
    @PostMapping("/scontrino/{idCliente}")
    public Scontrino creaScontrino(@PathVariable Long idCliente) throws UserNotFoundException {
        Cliente cliente = clienteService.getClienteById(idCliente);
        List<Acquisto> acquisti = acquistoRepository.findByCliente(cliente);
        Scontrino scontrino = scontrinoService.creaScontrino(idCliente);
        return scontrino;
    }



    @Override
    @GetMapping("/{id}")
    public ScontrinoDto getScontrinoById(@PathVariable Long id) {
        Scontrino scontrino = scontrinoService.getScontrinoById(id);
        return scontrinoMapper.asDTO(scontrino);
    }
}