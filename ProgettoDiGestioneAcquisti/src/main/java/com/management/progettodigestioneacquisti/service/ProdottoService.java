package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProdottoService {
    private final ProdottoRepository prodottoRepository;

    public Prodotto createProduct(Prodotto prodotto, MultipartFile file) throws IOException {

        byte[] immagine = file.getBytes();
        prodotto.setImmagine(immagine);
        prodotto.setId(prodotto.getId());
        prodotto.setNome(prodotto.getNome());
        prodotto.setPrezzoUnitario(prodotto.getPrezzoUnitario());
        prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile());
        prodotto.setImmagine(prodotto.getImmagine());
        return prodottoRepository.save(prodotto);

    }

    public Prodotto getProdottoById(String ean) {
        return prodottoRepository.findProdottoByEanProdotto(ean);
    }

    public List<Prodotto> getAllProducts() {
        return prodottoRepository.findAll();
    }

    public void updateQuantityDopoAcquisto(Prodotto prodotto, Acquisto acquisto) throws ProductNotFoundException {
        int nuovaQuantita = prodotto.getQuantitaDisponibile() - acquisto.getQuantitaAcquistata();
        if (nuovaQuantita == 0) {
            throw new ProductNotFoundException("Quantità esaurita");
        }
        prodotto.setQuantitaDisponibile(Math.max(0, nuovaQuantita));
        prodottoRepository.save(prodotto);
    }

    public Prodotto saveProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    public ProdottoDto asDtoConImmagine(Prodotto prodotto) {
        ProdottoDto prodottoDto = new ProdottoDto();
        prodottoDto.setId(prodotto.getId());
        prodottoDto.setNome(prodotto.getNome());
        prodottoDto.setPrezzoUnitario(prodotto.getPrezzoUnitario());
        prodottoDto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile());

        // Converti array di byte dell'immagine in una stringa Base64
        String imageBase64 = Base64.getEncoder().encodeToString(prodotto.getImmagine());
        prodottoDto.setImmagine(imageBase64.getBytes());

        return prodottoDto;
    }


    public void deleteProdotto(String ean) {
        Optional<Prodotto> prodottoOp = prodottoRepository.findById(ean);
        if (prodottoOp.isPresent()) {
            Prodotto prodotto = prodottoOp.get();
            prodottoRepository.delete(prodotto);
        } else {
            throw new EntityNotFoundException("Prodotto non trovato");
        }
    }

    public void updateProdotto(Prodotto prodotto, Double percentualeSconto) {
        Prodotto prodottoEsistente = prodottoRepository.findProdottoByEanProdotto(prodotto.getEanProdotto());
        StringBuilder sc = new StringBuilder();

        prodottoEsistente.setNome(prodotto.getNome());
        prodottoEsistente.setPrezzoUnitario(prodotto.getPrezzoUnitario());

        BigDecimal percentualeScontoDecimal = BigDecimal.valueOf(percentualeSconto);
        BigDecimal cento = BigDecimal.valueOf(100);

        BigDecimal prezzoUnitario = prodotto.getPrezzoUnitario();
        BigDecimal sconto = prezzoUnitario.multiply(percentualeScontoDecimal).divide(cento);
        BigDecimal prezzoScontato = prezzoUnitario.subtract(sconto);
        prodottoEsistente.setPrezzoScontato(prezzoScontato);

        if (sconto.compareTo(BigDecimal.ZERO) > 0) {
            prodottoEsistente.setScontato(String.valueOf(sc.append("Questo prodotto è stato scontato al:").append(percentualeSconto).append("%")));
            prodottoEsistente.setStatoSconto(true);
        }

        prodottoRepository.save(prodottoEsistente);
    }

    @Transactional
    public void aggiornaQuantitaDisponibile(String eanProdotto, int quantitaDaAggiungere) {
        Optional<Prodotto> optionalProduct = Optional.ofNullable(prodottoRepository.findProdottoByEanProdotto(eanProdotto));
        if (optionalProduct.isPresent()) {
            Prodotto product = optionalProduct.get();
            int quantitaDisponibile = product.getQuantitaDisponibile();
            int nuovaQuantita = quantitaDisponibile + quantitaDaAggiungere;
            product.setQuantitaDisponibile(nuovaQuantita);
            prodottoRepository.save(product);
        } else {
            throw new RuntimeException("Prodotto non trovato con EAN " + eanProdotto);
        }
    }

}


