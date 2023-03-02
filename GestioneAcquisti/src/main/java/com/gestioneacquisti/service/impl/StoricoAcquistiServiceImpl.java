package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.StoricoAcquistiDao;
import com.gestioneacquisti.model.StoricoAcquisti;
import com.gestioneacquisti.service.StoricoAcquistiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StoricoAcquistiServiceImpl implements StoricoAcquistiService {
    private final StoricoAcquistiDao storicoAcquistiDao;

    @Override
    public StoricoAcquisti save(StoricoAcquisti storicoAcquisti) {
        return storicoAcquistiDao.save(storicoAcquisti);
    }

}


