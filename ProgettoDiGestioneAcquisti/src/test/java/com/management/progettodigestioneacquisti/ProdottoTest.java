package com.management.progettodigestioneacquisti;

import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdottoTest {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Test
    public void testSalvaProdotto1() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome("Panettone");
        prodotto.setPrezzoUnitario(new BigDecimal("10.00"));
        prodotto.setQuantitaDisponibile(30);
        Path imagePath = Paths.get("src/main/resources/images/panettone.jpg");
        byte[] immagineBytes = Files.readAllBytes(imagePath);
        prodotto.setImmagine(immagineBytes);
        Prodotto prodottoSalvato = prodottoRepository.save(prodotto);
        assertNotNull(prodottoSalvato.getId());
    }

    @Test
    public void testSalvaProdotto2() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome("Pane");
        prodotto.setPrezzoUnitario(new BigDecimal("5.00"));
        prodotto.setQuantitaDisponibile(10);
        Path imagePath = Paths.get("src/main/resources/images/pane.jpeg");
        byte[] immagineBytes = Files.readAllBytes(imagePath);
        prodotto.setImmagine(immagineBytes);
        Prodotto prodottoSalvato = prodottoRepository.save(prodotto);
        assertNotNull(prodottoSalvato.getId());
    }

    @Test
    public void testSalvaProdotto3() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome("Cioccolatini");
        prodotto.setPrezzoUnitario(new BigDecimal("15.00"));
        prodotto.setQuantitaDisponibile(20);
        Path imagePath = Paths.get("src/main/resources/images/cioccolatini.jpg");
        byte[] immagineBytes = Files.readAllBytes(imagePath);
        prodotto.setImmagine(immagineBytes);
        Prodotto prodottoSalvato = prodottoRepository.save(prodotto);
        assertNotNull(prodottoSalvato.getId());
    }

    @Test
    public void testSalvaProdotto4() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome("Cake");
        prodotto.setPrezzoUnitario(new BigDecimal("20.00"));
        prodotto.setQuantitaDisponibile(10);
        Path imagePath = Paths.get("src/main/resources/images/cake.jpeg");
        byte[] immagineBytes = Files.readAllBytes(imagePath);
        prodotto.setImmagine(immagineBytes);
        Prodotto prodottoSalvato = prodottoRepository.save(prodotto);
        assertNotNull(prodottoSalvato.getId());
    }
    @Test
    public void testSalvaProdotto5() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome("Smart Tv");
        prodotto.setPrezzoUnitario(new BigDecimal("5000.00"));
        prodotto.setQuantitaDisponibile(10);
        Path imagePath = Paths.get("src/main/resources/images/smart.jpeg");
        byte[] immagineBytes = Files.readAllBytes(imagePath);
        prodotto.setImmagine(immagineBytes);
        Prodotto prodottoSalvato = prodottoRepository.save(prodotto);
        assertNotNull(prodottoSalvato.getId());
    }
}
