package com.management.progettodigestioneacquisti.controller;

import com.management.progettodigestioneacquisti.dto.FidelityCardDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Fidelity Card API")
public interface FidelityCardController {
    @ApiOperation("Salva fidelity card")
    FidelityCardDto createCard(@RequestBody FidelityCardDto fidelityCardDto, @PathVariable Long cliente_id);
}
