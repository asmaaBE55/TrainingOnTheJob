package com.management.progettodigestioneacquisti.batchdatabasetocsv;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfigurationExport {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<ProdottoDto> jdbcCursorItemReader() {
        JdbcCursorItemReader<ProdottoDto> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("SELECT ean_prodotto, nome FROM prodotti");
        itemReader.setRowMapper(new BeanPropertyRowMapper<>(ProdottoDto.class));
        return itemReader;
    }

    @Bean
    public FlatFileItemWriter<ProdottoDto> flatFileItemWriter() {
        FlatFileItemWriter<ProdottoDto> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(new FileSystemResource("prodotti.csv"));
        itemWriter.setLineAggregator(new ProdottoDtoLineAggregator());
        return itemWriter;
    }

    @Bean
    public Step databaseToCsvStep() {
        return stepBuilderFactory.get("databaseToCsvStep")
                .<ProdottoDto, ProdottoDto>chunk(1)
                .reader(jdbcCursorItemReader())
                .writer(flatFileItemWriter())
                .build();
    }

    @Bean
    public Job databaseToCsvJob() {
        return jobBuilderFactory.get("databaseToCsvJob")
                .incrementer(new RunIdIncrementer())
                .start(databaseToCsvStep())
                .build();
    }

}
