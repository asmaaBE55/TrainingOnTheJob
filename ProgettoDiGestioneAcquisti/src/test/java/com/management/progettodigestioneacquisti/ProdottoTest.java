package com.management.progettodigestioneacquisti;

import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdottoTest {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Test
    public void testSalvaProdotto() {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome("Prodotto di test");
        prodotto.setPrezzoUnitario(new BigDecimal("10.00"));
        prodotto.setQuantitaDisponibile(100);

        Prodotto prodottoSalvato = prodottoRepository.save(prodotto);
        assertNotNull(prodottoSalvato.getId());
    }
    @Test
    public void testSalvaProdotto1() {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome("Prodotto di test2 creato dal JUnit");
        prodotto.setPrezzoUnitario(new BigDecimal("2.00"));
        prodotto.setQuantitaDisponibile(10);

        Prodotto prodottoSalvato = prodottoRepository.save(prodotto);
        assertNotNull(prodottoSalvato.getId());
    }
}
