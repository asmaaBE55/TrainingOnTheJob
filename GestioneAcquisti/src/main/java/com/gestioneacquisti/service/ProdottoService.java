package com.gestioneacquisti.service;

import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Prodotto;

import java.util.List;

public interface ProdottoService {
    Prodotto createProduct(Prodotto prodotto);

    Prodotto findById(Long id) throws ProductNotFoundException;

    List<Prodotto> getAllProducts();

    void updateProdottoQuantita(Prodotto prodotto) throws ProductNotFoundException;
}
