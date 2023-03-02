package com.gestioneacquisti.service;

import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.dto.ProdottoDto;
import com.gestioneacquisti.exception.InsufficientFundsException;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Acquisto;
import com.gestioneacquisti.model.Cliente;
import com.gestioneacquisti.model.Prodotto;

import java.util.List;

public interface AcquistoService {
    void compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata) throws InsufficientFundsException, ProductNotFoundException;

    //List<Acquisto> compraProdotti(Cliente cliente, List<Prodotto> prodotti) throws InsufficientFundsException, ProductNotFoundException;

    List<AcquistoDto> compraProdotti(Cliente cliente, List<ProdottoDto> prodotti) throws InsufficientFundsException, ProductNotFoundException;

    Acquisto findById(Long id);

    Acquisto save(Acquisto acquisto);
    List<Acquisto> findAll();

}
