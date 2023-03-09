package com.management.progettodigestioneacquisti.controller;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Prodotto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = "Prodotto API")
public interface ProdottoController {

    @ApiOperation("Crea un nuovo prodotto")
    ProdottoDto createProduct(@RequestBody @Valid ProdottoDto prodottoDto, @RequestParam("file") MultipartFile file, BindingResult result) throws IOException;

    @ApiOperation("Get prodotto tramite ID")
    ProdottoDto findById(@PathVariable Long id) throws ProductNotFoundException;

    @ApiOperation("Get tutti i prodotti")
    List<ProdottoDto> list() throws IOException;

    @ApiOperation("Rimuovi questo prodotto")
    ResponseEntity<?> delete(@PathVariable("id") Long id);

    @ApiOperation("Aggiungi uno sconto ad un prodotto")
    ProdottoDto updateProdotto(@PathVariable Long id, @RequestParam Double sconto) throws ChangeSetPersister.NotFoundException;
}
