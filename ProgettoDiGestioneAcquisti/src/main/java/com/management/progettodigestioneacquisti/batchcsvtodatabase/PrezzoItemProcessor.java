package com.management.progettodigestioneacquisti.batchcsvtodatabase;

import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PrezzoItemProcessor implements ItemProcessor<PrezzoDto, Prodotto> {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Override
    public Prodotto process(PrezzoDto prezzoDto) throws Exception {
        Prodotto prodotto = prodottoRepository.findProdottoByEanProdotto(prezzoDto.getEanProdotto());

        if (prodotto != null) {
            BigDecimal prezzoFornitore = new BigDecimal(String.valueOf(prezzoDto.getPrezzoFornitore()));
            prezzoDto.setPrezzoFornitore(prezzoFornitore);
            prodotto.setPrezzoFornitore(prezzoDto.getPrezzoFornitore());
        }
        return prodotto;
    }

}
