package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoricoAcquistiRepository extends JpaRepository<StoricoAcquisti, Long> {
    StoricoAcquisti findStoricoAcquistiById(Long id);

    @Query(value = "SELECT p.prodotto_id AS id_prodotto, COUNT(*) AS numero_acquisti " +
            "FROM Acquisti a JOIN Clienti c ON a.cliente_id = c.cliente_id " +
            "JOIN Prodotti p ON a.acquisto_id = p.prodotto_id " +
            "GROUP BY p.prodotto_id", nativeQuery = true)
    List<StoricoAcquisti> getStoricoAcquistiGroupById();

    List<StoricoAcquisti> getStoricoAcquistiByCliente(Cliente cliente);

    StoricoAcquisti findByNomeProdotto(String nomeProdotto);
}
