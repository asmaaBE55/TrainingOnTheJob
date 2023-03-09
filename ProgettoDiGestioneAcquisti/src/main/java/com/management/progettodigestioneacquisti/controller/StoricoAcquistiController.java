package com.management.progettodigestioneacquisti.controller;

import com.management.progettodigestioneacquisti.dto.StoricoAcquistiDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "Storico API")
public interface StoricoAcquistiController {
    @ApiOperation("Salva storico acquisti")
    StoricoAcquistiDto createStorico(@RequestBody StoricoAcquistiDto storicoAcquistiDto);

    @ApiOperation("Esporta la lista di acquisti in un file excel")
    void exportAcquistiToExcel(HttpServletResponse response) throws IOException;

    @ApiOperation("Get storico grouped by id")
    List<StoricoAcquistiDto> list();
}
