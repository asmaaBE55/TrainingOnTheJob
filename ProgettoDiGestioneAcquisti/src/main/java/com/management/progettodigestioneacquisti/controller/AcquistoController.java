package com.management.progettodigestioneacquisti.controller;


import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Api(tags = "Acquisti API")
public interface AcquistoController {

    @ApiOperation("Compra uno o più prodotti")
    ResponseEntity<?> compraProdotti(@PathVariable Long id, @RequestBody List<ProdottoDto> prodotti, BindingResult result);

    @ApiOperation("Get acquisti fatte da un cliente in un certo periodo")
    ResponseEntity<String> getAcquistiByClienteIdAndPeriodoAsString(@PathVariable Long clienteId,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine);
}
