package com.management.progettodigestioneacquisti.batchdatabasetocsv;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class ProdottoCsvWriter extends FlatFileItemWriter<ProdottoDto> {

    @Value("${export.file.path}")
    private String exportFilePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource outputResource = new FileSystemResource(exportFilePath);
        setResource(outputResource);
        setLineAggregator(new DelimitedLineAggregator<ProdottoDto>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<ProdottoDto>() {
                    {
                        setNames(new String[]{"eanProdotto", "nome"});
                    }
                });
            }
        });
        super.afterPropertiesSet();
    }
}

