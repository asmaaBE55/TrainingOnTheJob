package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.StoricoAcquistiDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Api(tags = "Storico API")
public interface StoricoAcquistiController {
    @ApiOperation("Salva storico acquisti")
    StoricoAcquistiDto createProduct(@RequestBody StoricoAcquistiDto storicoAcquistiDto);

    @ApiOperation("Get storico grouped by id")
    List<StoricoAcquistiDto> list();
}
