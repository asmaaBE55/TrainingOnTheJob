package com.management.progettodigestioneacquisti.controller;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Prodotto API")
public interface ProdottoController {

    @ApiOperation("Crea un nuovo prodotto")
    ProdottoDto createProduct(@RequestBody @Valid ProdottoDto prodottoDto, BindingResult result);

    @ApiOperation("Get prodotto tramite ID")
    ProdottoDto findById(@PathVariable Long id) throws ProductNotFoundException;

    @ApiOperation("Get tutti i prodotti")
    List<ProdottoDto> list();
}
