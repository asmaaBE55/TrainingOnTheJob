package com.management.progettodigestioneacquisti.batchcsvtodatabase;

import com.management.progettodigestioneacquisti.model.Prodotto;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Bean
    public FlatFileItemReader<PrezzoDto> prezzoCsvFileItemReader() {
        FlatFileItemReader<PrezzoDto> itemReader = new FlatFileItemReader<>();
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new ClassPathResource("prezzo.csv"));
        itemReader.setLineMapper(new DefaultLineMapper<PrezzoDto>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("eanProdotto", "prezzoFornitore");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<PrezzoDto>() {{
                setTargetType(PrezzoDto.class);
            }});
        }});
        return itemReader;
    }

    @Bean
    public JdbcBatchItemWriter<Prodotto> prezzoDatabaseItemWriter() {
        JdbcBatchItemWriter<Prodotto> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.setSql("UPDATE prodotto SET prezzo_fornitore = :prezzoFornitore WHERE ean_prodotto = :eanProdotto");
        itemWriter.setDataSource(dataSource);
        return itemWriter;
    }

    @Bean
    public Step prezzoCsvToDatabaseStep() {
        return stepBuilderFactory.get("prezzoCsvToDatabaseStep")
                .<PrezzoDto, Prodotto>chunk(10)
                .reader(prezzoCsvFileItemReader())
                .processor(prezzoItemProcessor())
                .writer(prezzoDatabaseItemWriter())
                .build();
    }

    @Bean
    public ItemProcessor<PrezzoDto, Prodotto> prezzoItemProcessor() {
        return new ItemProcessor<PrezzoDto, Prodotto>() {
            @Override
            public Prodotto process(PrezzoDto prezzoDto) throws Exception {
                Prodotto prodotto = prodottoRepository.findProdottoByEanProdotto(prezzoDto.getEanProdotto());
                if (prodotto != null) {
                    BigDecimal prezzoFornitore = new BigDecimal(String.valueOf(prezzoDto.getPrezzoFornitore()));
                    prodotto.setPrezzoFornitore(prezzoFornitore);
                }
                return prodotto;
            }
        };
    }

    @Bean
    public Job prezzoCsvToDatabaseJob() {
        return jobBuilderFactory.get("prezzoCsvToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .flow(prezzoCsvToDatabaseStep())
                .end()
                .build();
    }
}
