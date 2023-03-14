package com.management.progettodigestioneacquisti.batchcsvtodatabase;

import org.springframework.batch.item.file.LineMapper;

import java.math.BigDecimal;

public class PrezzoLineMapper implements LineMapper<PrezzoDto> {

    private static final String DELIMITER = ",";

    @Override
    public PrezzoDto mapLine(String line, int lineNumber) throws Exception {
        String[] fields = line.split(DELIMITER);

        PrezzoDto prezzo = new PrezzoDto();
        prezzo.setEanProdotto(fields[0]);
        prezzo.setPrezzoFornitore(new BigDecimal(fields[1]));

        return prezzo;
    }
}
