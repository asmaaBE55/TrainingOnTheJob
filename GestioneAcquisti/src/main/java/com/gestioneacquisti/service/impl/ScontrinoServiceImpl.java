package com.gestioneacquisti.service.impl;


import com.gestioneacquisti.dao.ScontrinoDao;
import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.model.Scontrino;
import com.gestioneacquisti.service.ClienteService;
import com.gestioneacquisti.service.ProdottoService;
import com.gestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ScontrinoServiceImpl implements ScontrinoService {
    private final ScontrinoDao scontrinoDao;
    private final ClienteService clienteService;
    private final ProdottoService prodottoService;

    @Override
    public void creaScontrino(Long clienteId, AcquistoDto acquistoDto) throws ProductNotFoundException {
        Cliente cliente = clienteService.getCliente(clienteId);
        Scontrino scontrino = new Scontrino();
        scontrino.setCliente(cliente);
        for (AcquistoDto item : acquistoDto.getItems()) {
            Prodotto prodotto = prodottoService.findById(item.getId());
            int quantitaAcquistata = item.getQuantita();
            Acquisto acquisto = new Acquisto();
            acquisto.setProdotto(prodotto);
            acquisto.setQuantitaAcquistata(quantitaAcquistata);
            acquisto.setPrezzoDiAcquisto(acquisto.getPrezzoDiAcquisto());
            scontrino.addAcquisto(acquisto);
        }
        scontrinoDao.save(scontrino);
    }

}
