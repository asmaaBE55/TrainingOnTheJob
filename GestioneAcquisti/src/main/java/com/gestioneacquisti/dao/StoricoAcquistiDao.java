package com.gestioneacquisti.dao;

import com.gestioneacquisti.model.StoricoAcquisti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoricoAcquistiDao extends JpaRepository<StoricoAcquisti, Long> {
    @Query(value = "SELECT p.prodotto_id AS id_prodotto, COUNT(*) AS numero_acquisti " +
            "FROM Acquisti a JOIN Clienti c ON a.cliente_id = c.cliente_id " +
            "JOIN Prodotti p ON a.acquisto_id = p.prodotto_id " +
            "GROUP BY p.prodotto_id", nativeQuery = true)
    List<StoricoAcquisti> getStoricoAcquistiGroupById();
}
