package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.ClienteController;
import com.management.progettodigestioneacquisti.dto.ClienteDto;
import com.management.progettodigestioneacquisti.mapper.ClienteMapper;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.validators.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/clienti")
@RequiredArgsConstructor
public class ClienteControllerImpl implements ClienteController {
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;
    private final ClienteValidator clienteValidator;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto save(@RequestBody @Valid ClienteDto clienteDto, BindingResult result) {
        clienteValidator.validate(clienteDto, result);
        if (result.hasErrors()) {
            throw new ValidationException("Dati prodotto non validi" + result);
        }
        Cliente cliente = clienteMapper.asEntity(clienteDto);
        return clienteMapper.asDTO(clienteService.saveCliente(cliente));
    }

    @Override
    @GetMapping("/{id}")
    public ClienteDto getCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return clienteMapper.asDTO(cliente);
    }

    @Override
    @GetMapping
    public List<ClienteDto> list() {
        return clienteMapper.asDTOlist(clienteService.findAll());
    }
}
