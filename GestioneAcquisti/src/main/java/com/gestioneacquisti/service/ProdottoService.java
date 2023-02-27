package com.gestioneacquisti.service;

import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Prodotto;

import java.util.List;
import java.util.Optional;

public interface ProdottoService {
    Prodotto createProduct(Prodotto prodotto);

    Optional<Prodotto> findById(Long id) throws ProductNotFoundException;

    List<Prodotto> getAllProducts();

    void updateProdottoQuantita(Prodotto prodotto) throws ProductNotFoundException;
}
