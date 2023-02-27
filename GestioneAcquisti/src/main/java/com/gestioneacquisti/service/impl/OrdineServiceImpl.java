package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.OrdineDao;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Ordine;
import com.gestioneacquisti.service.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Service
public class OrdineServiceImpl implements OrdineService {
    private final OrdineDao ordineDao;
    @Override
    public Ordine creaOrdine(Cliente cliente, List<Acquisto> acquisti) {
        Ordine ordine = new Ordine();
        ordine.setCliente(cliente);
        ordine.setAcquisti(acquisti);
        ordine.setStato(Ordine.StatoOrdine.IN_CORSO);
        ordine.setTotale(acquisti.stream()
                .map(prodotto -> prodotto.getPrezzoDiAcquisto()
                        .multiply(BigDecimal.valueOf(prodotto.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
        return ordineDao.save(ordine);
    }
    @Override
    public void aggiornaOrdine(Ordine ordine, Ordine.StatoOrdine nuovoStato) {
        if (ordine == null || nuovoStato == null) {
            throw new IllegalArgumentException("Ordine o stato nullo");
        }
        if (ordine.getStato() == nuovoStato) {
            return; // Lo stato dell'ordine è già aggiornato
        }
        ordine.setStato(nuovoStato);
        ordineDao.save(ordine);
    }

}
