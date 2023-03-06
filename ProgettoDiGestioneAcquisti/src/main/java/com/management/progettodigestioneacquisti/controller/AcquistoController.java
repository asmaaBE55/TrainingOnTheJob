package com.management.progettodigestioneacquisti.controller;


import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Acquisti API")
public interface AcquistoController {

    @ApiOperation("Azione compra prodotto")
    ResponseEntity<String> compraProdotto(@PathVariable Long idCliente, @RequestBody @Valid ProdottoDto dtoProdotti, BindingResult result, @RequestParam(value = "quantitaDesiderata", defaultValue = "1") int quantitaDesiderata) throws InsufficientFundsException, ProductNotFoundException;

    @ApiOperation("Compra lista di prodotti")
    ResponseEntity<?> compraProdotti(@PathVariable Long id, @RequestBody List<ProdottoDto> prodotti, BindingResult result);

}
