package com.gestioneacquisti.controller;

import com.gestioneacquisti.dto.StoricoAcquistiDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Storico API")
public interface StoricoAcquistiController {
    @ApiOperation("Salva storico acquisti")
    StoricoAcquistiDto createProduct(@RequestBody StoricoAcquistiDto storicoAcquistiDto);

    @ApiOperation("Get storico grouped by id")
    List<StoricoAcquistiDto> list();
}
