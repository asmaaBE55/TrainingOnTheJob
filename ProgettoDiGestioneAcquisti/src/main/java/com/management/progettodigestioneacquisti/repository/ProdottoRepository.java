package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdottoRepository extends JpaRepository<Prodotto, String> {
    boolean existsByNome(String nome);

    List<Prodotto> findByStatoSconto(boolean statoSconto);

    boolean existsProdottoByEanProdotto(String eanProdotto);

    Prodotto findProdottoByEanProdotto(String eanProdotto);

    Optional<Prodotto> findByEanProdotto(String ean);
}
