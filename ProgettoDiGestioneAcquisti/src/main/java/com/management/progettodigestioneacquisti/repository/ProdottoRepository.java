package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    Prodotto findProdottoById(Long id);

    boolean existsByNome(String nome);

    boolean existsProdottoById(Long id);
}
