package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ProdottoService {
    private final ProdottoRepository prodottoRepository;
    public Prodotto createProduct(Prodotto prodotto,MultipartFile file) throws IOException {
        byte[] immagine = file.getBytes();
        prodotto.setImmagine(immagine);
        prodotto.setId(prodotto.getId());
        prodotto.setNome(prodotto.getNome());
        prodotto.setPrezzoUnitario(prodotto.getPrezzoUnitario());
        prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile());
        prodotto.setImmagine(prodotto.getImmagine());
        return prodottoRepository.save(prodotto);
    }
    public Prodotto getProdottoById(Long id) {
        return prodottoRepository.findProdottoById(id);
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

    public Prodotto getProdottoByNome(String nomeProdottoAcquistato) {
        return prodottoRepository.findProductByNome(nomeProdottoAcquistato);
    }

    public ProdottoDto asDtoConImmagine(Prodotto prodotto) throws IOException {
        String uploadDirectory = "src/main/resources/images/";
        ProdottoDto prodottoDto = new ProdottoDto();
        prodottoDto.setId(prodotto.getId());
        prodottoDto.setNome(prodotto.getNome());
        prodottoDto.setPrezzoUnitario(prodotto.getPrezzoUnitario());
        prodottoDto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile());

        // Legge l'immagine dal file system
        String imageName = Base64.getEncoder().encodeToString(prodotto.getImmagine());
        prodottoDto.setImmagine(imageName.getBytes());

        return prodottoDto;
    }

    public void deleteProdotto(Long id) {
        Optional<Prodotto> prodottoOp = prodottoRepository.findById(id);
        if (prodottoOp.isPresent()) {
            Prodotto prodotto = prodottoOp.get();
            prodottoRepository.delete(prodotto);
        } else {
            throw new EntityNotFoundException("Prodotto non trovato");
        }
    }

    public Prodotto updateProdotto(Prodotto prodotto, Double percentualeSconto) throws ChangeSetPersister.NotFoundException {
        Prodotto prodottoEsistente = prodottoRepository.findById(prodotto.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        prodottoEsistente.setNome(prodotto.getNome());
        prodottoEsistente.setPrezzoUnitario(prodotto.getPrezzoUnitario());

        BigDecimal percentualeScontoDecimal = BigDecimal.valueOf(percentualeSconto);
        BigDecimal cento = BigDecimal.valueOf(100);

        BigDecimal prezzoUnitario = prodotto.getPrezzoUnitario();
        BigDecimal sconto = prezzoUnitario.multiply(percentualeScontoDecimal).divide(cento);
        BigDecimal prezzoScontato = prezzoUnitario.subtract(sconto);

        prodottoEsistente.setPrezzoUnitario(prezzoScontato);

        return prodottoRepository.save(prodottoEsistente);
    }



}

