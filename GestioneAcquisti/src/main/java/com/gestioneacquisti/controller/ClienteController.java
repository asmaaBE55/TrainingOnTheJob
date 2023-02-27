package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.ClienteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Cliente API")
public interface ClienteController {
    @ApiOperation("Crea un nuovo cliente")
    ClienteDto save(@RequestBody ClienteDto clienteDto);

    @ApiOperation("Get cliente tramite ID")
    ClienteDto getCliente(@PathVariable Long id);


    @ApiOperation("Get tutti i clienti")
    List<ClienteDto> list();
}
