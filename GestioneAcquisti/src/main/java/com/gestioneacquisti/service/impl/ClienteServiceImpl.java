package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.ClienteDao;
import com.gestioneacquisti.exception.UserNotFoundException;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * L'implementazione ClienteServiceImpl utilizza il ClienteDao e il ClienteMapper
 * per accedere ai dati dell'entità Cliente.
 * Il costruttore prende in ingresso un ClienteDao e un ClienteMapper.
 * La classe implementa i metodi definiti nell'interfaccia ClienteService,
 * e gestisce le eccezioni se il cliente non viene trovato. Inoltre, è annotata con
 *
 * @Service per indicare che è un bean di Spring e con @Transactional per gestire
 * la transazionalità dei metodi.
 */

@RequiredArgsConstructor
@Transactional
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteDao clienteDao;

    @Override
    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }

    @Override
    public Cliente getCliente(Long id) {
        return clienteDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

    }

    @Override
    public Cliente save(Cliente cliente) {
        cliente.setTipoCliente(Cliente.TipoCliente.NUOVO_CLIENTE);
        cliente.setNumeroAcquisti(0);
        cliente.setImportoTotaleSpeso(BigDecimal.ZERO);
        return clienteDao.save(cliente);
    }

    @Override
    public void deleteById(Long id) {
        clienteDao.deleteById(id);
    }

    @Override
    public void updateClientStatus(Cliente cliente, BigDecimal importoTotaleSpeso) {
        BigDecimal sogliaGold = new BigDecimal("1000.00");
        BigDecimal sogliaPlatinum = new BigDecimal("5000.00");

        if (importoTotaleSpeso.compareTo(sogliaPlatinum) > 0) {
            cliente.setTipoCliente(Cliente.TipoCliente.PLATINUM);
        } else if (importoTotaleSpeso.compareTo(sogliaGold) > 0) {
            cliente.setTipoCliente(Cliente.TipoCliente.GOLD);
        } else {
            cliente.setTipoCliente(Cliente.TipoCliente.AFFEZIONATO);
        }
        cliente.setImportoTotaleSpeso(importoTotaleSpeso);
        clienteDao.save(cliente);
    }

    @Override
    public void aggiornaBudget(Cliente cliente, BigDecimal totale) {
        BigDecimal nuovoBudget = cliente.getBudget().subtract(totale); // sottrai l'importo totale speso dal bilancio
        cliente.setBudget(nuovoBudget); // aggiorna il campo bilancio
        clienteDao.save(cliente);
    }

    @Override
    public void updateNumeroAcquisti(Cliente cliente) {
        cliente.setNumeroAcquisti(cliente.getNumeroAcquisti() + 1);
        clienteDao.save(cliente);
    }

    @Override
    public void updateImportoTotaleSpeso(Cliente cliente, BigDecimal prezzo) {
        BigDecimal importoTotaleSpeso = cliente.getImportoTotaleSpeso();
        if (importoTotaleSpeso == null) {
            importoTotaleSpeso = BigDecimal.ZERO;
        }
        cliente.setImportoTotaleSpeso(importoTotaleSpeso.add(prezzo));
        clienteDao.save(cliente);
    }

}

