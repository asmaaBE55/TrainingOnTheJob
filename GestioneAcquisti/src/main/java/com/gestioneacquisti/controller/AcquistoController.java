package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.dto.ProdottoDto;
import com.gestioneacquisti.model.Acquisto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Acquisti API")
public interface AcquistoController {
    @ApiOperation("Azione compra prodotto")
    ResponseEntity<String> compraProdotto(@PathVariable Long clienteId, @PathVariable Long prodottoId, @RequestParam int quantitaDesiderata);

//    @ApiOperation("Compra lista di prodotti")
//    ResponseEntity<?> compraProdotti(@RequestBody Acquisto compraRequest);

//    @ApiOperation("Compra lista di prodotti")
//    ResponseEntity<List<AcquistoDto>> compraProdotti(@PathVariable Long clienteId, @RequestBody List<ProdottoDto> prodotti);

    @ApiOperation("Get acquisti")
    List<AcquistoDto> getAcquisti();
}
