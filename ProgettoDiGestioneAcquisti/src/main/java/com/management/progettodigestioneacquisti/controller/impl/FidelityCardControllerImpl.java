package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.FidelityCardController;
import com.management.progettodigestioneacquisti.dto.FidelityCardDto;
import com.management.progettodigestioneacquisti.mapper.FidelityCardMapper;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.service.FidelityCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelity_card")
@RequiredArgsConstructor
public class FidelityCardControllerImpl implements FidelityCardController {
    private final ClienteService clienteService;
    private final FidelityCardService fidelityCardService;
    private final FidelityCardMapper fidelityCardMapper;

    @Override
    @PostMapping("fidelity_card/{cliente_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public FidelityCardDto createCard(@RequestBody FidelityCardDto fidelityCardDto, @PathVariable Long cliente_id) {
        clienteService.getClienteById(cliente_id);
        return fidelityCardMapper.asDTO(fidelityCardService.creaFidelityCard(cliente_id));
    }
}
