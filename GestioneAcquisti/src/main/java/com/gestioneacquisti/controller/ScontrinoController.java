package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.ScontrinoDto;
import com.gestioneacquisti.model.Scontrino;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Api(tags = "Scontrino API")
public interface ScontrinoController {
    @ApiOperation("Crea scontrino di un acquisto")
    public ResponseEntity<String> creaScontrinoDaAcquisto(@PathVariable Long acquistoId);

    @ApiOperation("Get scontrino by id")
    ScontrinoDto getScontrinoById(@PathVariable Long id);
}
