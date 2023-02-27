package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.ClienteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Cliente API")
public interface ClienteController {
    @ApiOperation("Crea un nuovo cliente")
    ClienteDto save(@RequestBody ClienteDto clienteDto);
}
