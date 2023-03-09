package com.management.progettodigestioneacquisti.controller.impl;

import com.management.progettodigestioneacquisti.controller.StoricoAcquistiController;
import com.management.progettodigestioneacquisti.dto.StoricoAcquistiDto;
import com.management.progettodigestioneacquisti.mapper.StoricoAcquistiMapper;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.repository.StoricoAcquistiRepository;
import com.management.progettodigestioneacquisti.service.StoricoAcquistiService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/storico_acquisti")
@RequiredArgsConstructor
public class StoricoAcquistiControllerImpl implements StoricoAcquistiController {
    private final StoricoAcquistiService storicoAcquistiService;
    private final StoricoAcquistiMapper storicoAcquistiMapper;
    private final StoricoAcquistiRepository storicoAcquistiRepository;
    private final AcquistoRepository acquistoRepository;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoricoAcquistiDto createStorico(@RequestBody StoricoAcquistiDto storicoAcquistiDto) {
        StoricoAcquisti storicoAcquisti = storicoAcquistiMapper.asEntity(storicoAcquistiDto);
        return storicoAcquistiMapper.asDTO(storicoAcquistiService.saveAcquisto(storicoAcquisti));
    }

    @Override
    @GetMapping("/acquisti/excel")
    public void exportAcquistiToExcel(HttpServletResponse response) throws IOException {
        List<Acquisto> acquisti = acquistoRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Acquisti");

        // Creazione dell'intestazione del foglio di lavoro
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Cliente");
        headerRow.createCell(1).setCellValue("Prodotto");
        headerRow.createCell(2).setCellValue("Quantità");
        headerRow.createCell(3).setCellValue("Prezzo di acquisto");

        // Riempimento del foglio di lavoro con i dati degli acquisti
        int rowNum = 1;
        for (Acquisto acquisto : acquisti) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(acquisto.getCliente().getNome());
            row.createCell(1).setCellValue(acquisto.getNomeProdottoAcquistato());
            row.createCell(2).setCellValue(acquisto.getQuantitaAcquistata());
            row.createCell(3).setCellValue(acquisto.getPrezzoDiAcquisto().doubleValue());
        }

        // Impostazione della larghezza delle colonne
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);

        // Impostazione del tipo di contenuto e del nome del file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=storico_acquisti.xlsx");

        // Scrittura del workbook di Excel sulla risposta HTTP
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    @GetMapping
    public List<StoricoAcquistiDto> list() {
        return storicoAcquistiMapper.asDTOlist(storicoAcquistiRepository.getStoricoAcquistiGroupById());
    }
}



