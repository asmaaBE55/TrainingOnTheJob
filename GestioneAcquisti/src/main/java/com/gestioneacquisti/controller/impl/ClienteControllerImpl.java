package com.gestioneacquisti.controller.impl;

import com.gestioneacquisti.controller.ClienteController;
import com.gestioneacquisti.dto.ClienteDto;
import com.gestioneacquisti.mapper.ClienteMapper;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienti")
@RequiredArgsConstructor
public class ClienteControllerImpl implements ClienteController {
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto save(@RequestBody ClienteDto clienteDto) {
        Cliente cliente = clienteMapper.asEntity(clienteDto);
        return clienteMapper.asDTO(clienteService.save(cliente));
    }

    @Override
    @GetMapping("/{id}")
    public ClienteDto getCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.getCliente(id);
        return clienteMapper.asDTO(cliente);
    }

    @Override
    @GetMapping
    public List<ClienteDto> list() {
        return clienteMapper.asDTOlist(clienteService.findAll());
    }
}
