package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProdottoService {
    private final ProdottoRepository prodottoRepository;

    public Prodotto createProduct(Prodotto prodotto) {
        prodotto.setId(prodotto.getId());
        prodotto.setNome(prodotto.getNome());
        prodotto.setPrezzoUnitario(prodotto.getPrezzoUnitario());
        prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile());
        return prodottoRepository.save(prodotto);
    }

    public Prodotto getProdottoById(Long id) {
        return prodottoRepository.findProdottoById(id);
    }

    public List<Prodotto> getAllProducts() {
        return prodottoRepository.findAll();
    }

    public void updateQuantityDopoAcquisto(Prodotto prodotto, Acquisto acquisto) {
        prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile() - acquisto.getQuantitaAcquistata());
        prodottoRepository.save(prodotto);
    }

    public boolean existsProdottoById(Long id) {
        return prodottoRepository.existsById(id);
    }

    public boolean existByName(String nome) {
        return prodottoRepository.existsByNome(nome);
    }

    public Prodotto saveProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }
}

