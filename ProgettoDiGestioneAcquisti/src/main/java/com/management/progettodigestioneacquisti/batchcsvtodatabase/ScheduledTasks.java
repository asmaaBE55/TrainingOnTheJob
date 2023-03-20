package com.management.progettodigestioneacquisti.batchcsvtodatabase;

import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import com.management.progettodigestioneacquisti.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Component
public class ScheduledTasks {

    @Autowired
    private ProdottoService prodottoService;
    @Autowired
    private ProdottoRepository prodottoRepository;

    public void importaPrezziFornitoriDalCsv() {
        LOGGER.info("Start importazione prezzi fornitori dal csv");
        prodottoService.importaPrezziFornitoriDalCsv();
        LOGGER.info("Fine importazione prezzi fornitori dal csv");
    }

    public void importaQuantitaFornitaDalCsv() {
        LOGGER.info("Start importazione quantità fornita dal csv");
        List<Prodotto> prodotti = prodottoRepository.findAll();
        for (Prodotto prodotto : prodotti) {
            if (prodotto.getQuantitaDisponibile() == 0) {
                prodottoService.importaQuantitaFornitaDalCsv();
                break;
            }
        }
        LOGGER.info("Fine importazione quantità fornita dal csv");
    }
}

