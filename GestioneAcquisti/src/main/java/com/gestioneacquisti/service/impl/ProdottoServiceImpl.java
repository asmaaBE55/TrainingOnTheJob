package com.gestioneacquisti.service.impl;

import com.gestioneacquisti.dao.ProdottoDao;
import com.gestioneacquisti.exception.ProductNotFoundException;
import com.gestioneacquisti.model.Prodotto;
import com.gestioneacquisti.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProdottoServiceImpl implements ProdottoService {
    private final ProdottoDao prodottoDao;

    @Override
    public Prodotto createProduct(Prodotto prodotto) {
        prodotto.setId(prodotto.getId());
        prodotto.setNome(prodotto.getNome());
        prodotto.setPrezzo(prodotto.getPrezzo());
        prodotto.setQuantita(prodotto.getQuantita());
        return prodottoDao.save(prodotto);
    }

    @Override
    public Prodotto findProductById(Long id) throws ProductNotFoundException {
        return prodottoDao.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

    }

    @Override
    public List<Prodotto> getAllProducts() {
        return prodottoDao.findAll();
    }

    @Override
    public Prodotto updateProdottoQuantita(Prodotto prodotto) throws ProductNotFoundException {
        if (prodotto.getQuantita() == 0) {
            throw new ProductNotFoundException(prodotto.getId());
        }
        // Aggiorno la quantità del prodotto
        prodotto.setQuantita(prodotto.getQuantita() - 1);

        // Salvo il prodotto aggiornato nel database
        return prodottoDao.save(prodotto);
    }

}
