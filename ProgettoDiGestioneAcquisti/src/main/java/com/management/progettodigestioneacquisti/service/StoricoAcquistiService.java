package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import com.management.progettodigestioneacquisti.repository.StoricoAcquistiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class StoricoAcquistiService {
    private final StoricoAcquistiRepository storicoAcquistiRepository;

    public StoricoAcquisti saveAcquisto(StoricoAcquisti storicoAcquisti) {
        return storicoAcquistiRepository.save(storicoAcquisti);
    }

    public List<StoricoAcquisti> saveAllAcquisti(List<StoricoAcquisti> storicoAcquistiList) {
        return storicoAcquistiRepository.saveAll(storicoAcquistiList);

    }
}
