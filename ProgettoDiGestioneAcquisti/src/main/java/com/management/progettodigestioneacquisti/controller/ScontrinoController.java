package com.management.progettodigestioneacquisti.controller;


import com.management.progettodigestioneacquisti.dto.ScontrinoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Scontrino API")
public interface ScontrinoController {
    @ApiOperation("Crea scontrino di un acquisto")
    ResponseEntity<String> creaScontrinoDaAcquisto(@PathVariable Long acquistoId);

    @ApiOperation("Get scontrino by id")
    ScontrinoDto getScontrinoById(@PathVariable Long id);
}
