package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.AcquistoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Scontrino API")
public interface ScontrinoController {
    @ApiOperation("Crea scontrino di un acquisto")
    public ResponseEntity<String> creaScontrinoDaAcquisto(@PathVariable Long acquistoId) ;

    }
