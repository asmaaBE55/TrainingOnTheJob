package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.AcquistoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Acquisti API")
public interface AcquistoController {
    @ApiOperation("Azione compra prodotto")
    ResponseEntity<String> compraProdotto(@PathVariable Long clienteId, @PathVariable Long prodottoId, @RequestParam int quantitaDesiderata);
}
