package com.gestioneacquisti.dao;

import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdineDao extends JpaRepository<Ordine, Long> {
    Optional<Ordine> findByClienteAndStato(Cliente cliente, Ordine.StatoOrdine stato);


}
