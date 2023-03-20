package com.management.progettodigestioneacquisti.batchcsvtodatabase;

import com.management.progettodigestioneacquisti.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Component
public class ScheduledTasks {

    @Autowired
    private ProdottoService prodottoService;

    public void importaPrezziFornitoriDalCsv() {
        LOGGER.info("Start importazione prezzi fornitori dal csv");
        prodottoService.importaPrezziFornitoriDalCsv();
        LOGGER.info("Fine importazione prezzi fornitori dal csv");
    }

    public void importaQuantitaFornitaDalCsv() {
        LOGGER.info("Start importazione quantità fornita dal csv");
        prodottoService.importaQuantitaFornitaDalCsv();
        LOGGER.info("Fine importazione quantità fornita dal csv");
    }
}

