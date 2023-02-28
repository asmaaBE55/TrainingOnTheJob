package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.AcquistoDao;
import com.gestioneacquisti.dao.StoricoAcquistiDao;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.StoricoAcquisti;
import com.gestioneacquisti.service.StoricoAcquistiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Transactional
@Service
public class StoricoAcquistiServiceImpl implements StoricoAcquistiService {
    private final AcquistoDao acquistoDao;
    private final StoricoAcquistiDao storicoAcquistiDao;
    @Override
    public void salvaAcquisto(Acquisto acquisto) {
        acquistoDao.save(acquisto);
    }
    @Override
    public void salvaStoricoAcquisti(Acquisto acquisto) {
        StoricoAcquisti storicoAcquisti = new StoricoAcquisti();
        storicoAcquistiDao.save(storicoAcquisti);
    }
}
