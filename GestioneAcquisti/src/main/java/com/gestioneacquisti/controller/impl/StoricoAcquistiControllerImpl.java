package com.gestioneacquisti.controller.impl;

import com.gestioneacquisti.controller.StoricoAcquistiController;
import com.gestioneacquisti.dao.StoricoAcquistiDao;
import com.gestioneacquisti.dto.StoricoAcquistiDto;
import com.gestioneacquisti.mapper.StoricoAcquistiMapper;
import com.gestioneacquisti.model.StoricoAcquisti;
import com.gestioneacquisti.service.StoricoAcquistiService;
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
    private final StoricoAcquistiDao storicoAcquistiDao;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoricoAcquistiDto createProduct(@RequestBody StoricoAcquistiDto storicoAcquistiDto) {
        StoricoAcquisti storicoAcquisti = storicoAcquistiMapper.asEntity(storicoAcquistiDto);
        return storicoAcquistiMapper.asDTO(storicoAcquistiService.save(storicoAcquisti));
    }

    @Override
    @GetMapping
    public List<StoricoAcquistiDto> list() {
        return storicoAcquistiMapper.asDTOlist(storicoAcquistiDao.getStoricoAcquistiGroupById());
    }
}
