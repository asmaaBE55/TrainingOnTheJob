package com.management.progettodigestioneacquisti.controller;

import com.management.progettodigestioneacquisti.dto.ClienteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cliente API")
public interface ClienteController {
    @ApiOperation("Crea un nuovo cliente")
    ClienteDto save(@RequestBody @Valid ClienteDto clienteDto, BindingResult result);

    @ApiOperation("Get cliente tramite ID")
    ClienteDto getCliente(@PathVariable Long id);


    @ApiOperation("Get tutti i clienti")
    List<ClienteDto> list();
}
