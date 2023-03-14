package com.management.progettodigestioneacquisti.batchdatabasetocsv;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job databaseToCsvJob;

    @Autowired
    private ProdottoCsvWriter prodottoCsvWriter;

    @GetMapping
    public ResponseEntity<String> export() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("timestamp", new Date())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(databaseToCsvJob, jobParameters);

        ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();

        @SuppressWarnings("unchecked")
        List<ProdottoDto> prodotti = (List<ProdottoDto>) jobExecutionContext.get("prodotti");
        prodottoCsvWriter.open(new ExecutionContext());
        assert prodotti != null;
        prodottoCsvWriter.write(prodotti);
        prodottoCsvWriter.close();

        return ResponseEntity.ok("Export completed successfully");
    }

}

