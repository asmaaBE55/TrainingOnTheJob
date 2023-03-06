package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Long id) {
        return clienteRepository.findClienteById(id);
    }

    public Cliente saveCliente(Cliente cliente) {
        cliente.setTipoCliente(Cliente.TipoCliente.NUOVO_CLIENTE);
        cliente.setNumeroAcquisti(0);
        cliente.setImportoTotaleSpeso(BigDecimal.ZERO);
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

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
        clienteRepository.save(cliente);
    }

    public void aggiornaBudget(Cliente cliente, BigDecimal totale) throws InsufficientFundsException {
        if (cliente.getBudget().compareTo(BigDecimal.ZERO) == 0){
            throw new InsufficientFundsException("Budget insufficiente");
        }
        BigDecimal nuovoBudget = cliente.getBudget().subtract(totale); // sottrai l'importo totale speso dal bilancio
        if(cliente.getBudget().compareTo(totale)>0){
        cliente.setBudget(nuovoBudget); // aggiorna il campo bilancio
        clienteRepository.save(cliente);
    }
    }

    public void updateNumeroAcquisti(Cliente cliente, Acquisto acquisto) {
        cliente.setNumeroAcquisti(cliente.getNumeroAcquisti() + acquisto.getQuantitaAcquistata());
        clienteRepository.save(cliente);
    }

    public void updateImportoTotaleSpeso(Cliente cliente, BigDecimal prezzo) {
        BigDecimal importoTotaleSpeso = cliente.getImportoTotaleSpeso();
        if (importoTotaleSpeso == null) {
            importoTotaleSpeso = BigDecimal.ZERO;
        }
        cliente.setImportoTotaleSpeso(importoTotaleSpeso.add(prezzo));
        clienteRepository.save(cliente);
    }

    public boolean existsClienteById(Long id) {
        return clienteRepository.existsById(id);
    }

}
