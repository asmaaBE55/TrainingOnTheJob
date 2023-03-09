package com.management.progettodigestioneacquisti.controller;


import com.management.progettodigestioneacquisti.dto.ScontrinoDto;
import com.management.progettodigestioneacquisti.exception.UserNotFoundException;
import com.management.progettodigestioneacquisti.model.Scontrino;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "Scontrino API")
public interface ScontrinoController {
    @ApiOperation("Crea scontrino cliente")
    Scontrino creaScontrino(@PathVariable Long idCliente) throws UserNotFoundException;

    @ApiOperation("Get scontrino by id")
    ScontrinoDto getScontrinoById(@PathVariable Long id);

    @ApiOperation("Esporta lista scontrini in un file excel")
    void exportScontriniToExcel(HttpServletResponse response) throws IOException;
}
