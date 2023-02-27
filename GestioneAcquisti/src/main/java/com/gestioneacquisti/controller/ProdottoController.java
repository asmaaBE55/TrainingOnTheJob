package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.ProdottoDto;
import com.gestioneacquisti.exception.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Prodotto API")
public interface ProdottoController {

    @ApiOperation("Crea un nuovo prodotto")
    ProdottoDto createProduct(@RequestBody ProdottoDto prodottoDto);

    @ApiOperation("Get prodotto tramite ID")
    ProdottoDto findById(@PathVariable Long id) throws ProductNotFoundException;

    @ApiOperation("Get tutti i prodotti")
    List<ProdottoDto> list();
}
