package com.gestioneacquisti.service;

import com.gestioneacquisti.model.StoricoAcquisti;

import java.util.List;

public interface StoricoAcquistiService {
    StoricoAcquisti save(StoricoAcquisti storicoAcquisti);


    StoricoAcquisti saveAll(List<StoricoAcquisti> storicoAcquistiList);
}
