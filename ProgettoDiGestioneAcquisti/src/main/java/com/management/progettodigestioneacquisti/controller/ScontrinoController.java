package com.management.progettodigestioneacquisti.controller;


import com.management.progettodigestioneacquisti.dto.ScontrinoDto;
import com.management.progettodigestioneacquisti.exception.UserNotFoundException;
import com.management.progettodigestioneacquisti.input.ScontrinoInput;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Scontrino;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Scontrino API")
public interface ScontrinoController {
    @ApiOperation("Crea scontrino cliente")
    Scontrino creaScontrino(@PathVariable Long idCliente) throws UserNotFoundException;

    @ApiOperation("Get scontrino by id")
    ScontrinoDto getScontrinoById(@PathVariable Long id);
}
