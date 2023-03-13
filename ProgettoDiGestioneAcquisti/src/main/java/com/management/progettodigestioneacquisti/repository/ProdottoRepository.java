package com.management.progettodigestioneacquisti.repository;

import com.management.progettodigestioneacquisti.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    Prodotto findProdottoById(Long id);

    boolean existsByNome(String nome);

    boolean existsProdottoById(Long id);

    Prodotto findProductByNome(String nomeProdottoAcquistato);
    List<Prodotto> findByStatoSconto(boolean statoSconto);

}
