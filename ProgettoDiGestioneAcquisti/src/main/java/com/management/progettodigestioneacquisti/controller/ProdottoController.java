package com.management.progettodigestioneacquisti.controller;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.exception.ScontoProdottoNonLogico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = "Prodotto API")
public interface ProdottoController {

    @ApiOperation("Crea un nuovo prodotto")
    ProdottoDto createProduct(@RequestBody @Valid ProdottoDto prodottoDto, @RequestParam("file") MultipartFile file, BindingResult result) throws IOException;

    @ApiOperation("Get prodotto tramite ID")
    ProdottoDto findById(@PathVariable String ean) throws ProductNotFoundException;

    @ApiOperation("Get tutti i prodotti")
    List<ProdottoDto> list() throws IOException;

    @ApiOperation("Rimuovi questo prodotto")
    ResponseEntity<?> delete(@PathVariable("id") String ean);

    @ApiOperation("Aggiungi uno sconto ad un prodotto")
    ProdottoDto updateProdotto(@PathVariable String ean, @RequestParam Double sconto) throws ChangeSetPersister.NotFoundException, ScontoProdottoNonLogico;

    @ApiOperation("Aggiorna la quantità stock del prodotto")
    ResponseEntity<String> aggiornaQuantitaDisponibile(@PathVariable("ean") String eanProdotto,
                                                       @RequestParam("quantity") int quantitaDaAggiungere);

    @ApiOperation("Importa prezzi fornitore")
    void importaPrezziFornitoriDalCsv();

    @ApiOperation("Importa quantita fornita")
    void importaQuantitaFornita();

    @ApiOperation("Vedi foto prodotto")
    ResponseEntity<byte[]> getImmagineProdottoByEan(@PathVariable String ean);
}
