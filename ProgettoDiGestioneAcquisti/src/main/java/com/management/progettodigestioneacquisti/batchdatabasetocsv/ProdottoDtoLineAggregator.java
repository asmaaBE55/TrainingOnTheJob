package com.management.progettodigestioneacquisti.batchdatabasetocsv;


import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;


public class ProdottoDtoLineAggregator extends DelimitedLineAggregator<ProdottoDto> {

    private static final String DELIMITER = ",";

    @Override
    public String aggregate(ProdottoDto item) {
        return String.join((CharSequence) DELIMITER, (CharSequence) item.getEanProdotto());
    }

}
