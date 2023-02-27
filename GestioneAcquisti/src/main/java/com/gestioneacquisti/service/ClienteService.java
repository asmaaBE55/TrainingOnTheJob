package com.gestioneacquisti.service;

import com.gestioneacquisti.model.Cliente;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * L'interfaccia ClienteService definisce i metodi per accedere ai dati dell'entità Cliente.
 * In questo caso, abbiamo definito i metodi per recuperare un singolo cliente,
 * creare un nuovo cliente, aggiornare un cliente esistente,
 * eliminare un cliente e recuperare tutti i clienti.
 */
public interface ClienteService {

    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    List<Cliente> save(List<Cliente> entities);

    void deleteById(Long id);

    void updateClientStatus(Cliente cliente, BigDecimal importoTotaleSpeso);

    void aggiornaBudget(Cliente cliente, BigDecimal importoTotaleSpeso);

    void updateNumeroAcquisti(Cliente cliente);

    void updateImportoTotaleSpeso(Cliente cliente, BigDecimal prezzo);
}

