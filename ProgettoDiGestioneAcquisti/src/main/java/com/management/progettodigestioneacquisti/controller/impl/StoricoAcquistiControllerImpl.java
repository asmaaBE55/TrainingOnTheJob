package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.StoricoAcquistiController;
import com.management.progettodigestioneacquisti.dto.StoricoAcquistiDto;
import com.management.progettodigestioneacquisti.mapper.StoricoAcquistiMapper;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import com.management.progettodigestioneacquisti.repository.StoricoAcquistiRepository;
import com.management.progettodigestioneacquisti.service.StoricoAcquistiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storico_acquisti")
@RequiredArgsConstructor
public class StoricoAcquistiControllerImpl implements StoricoAcquistiController {
    private final StoricoAcquistiService storicoAcquistiService;
    private final StoricoAcquistiMapper storicoAcquistiMapper;
    private final StoricoAcquistiRepository storicoAcquistiRepository;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoricoAcquistiDto createStorico(@RequestBody StoricoAcquistiDto storicoAcquistiDto) {
        StoricoAcquisti storicoAcquisti = storicoAcquistiMapper.asEntity(storicoAcquistiDto);
        return storicoAcquistiMapper.asDTO(storicoAcquistiService.saveAcquisto(storicoAcquisti));
    }

    @Override
    @GetMapping
    public List<StoricoAcquistiDto> list() {
        return storicoAcquistiMapper.asDTOlist(storicoAcquistiRepository.getStoricoAcquistiGroupById());
    }
}
