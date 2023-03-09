package com.management.progettodigestioneacquisti.controller.impl;


import com.management.progettodigestioneacquisti.controller.ScontrinoController;
import com.management.progettodigestioneacquisti.dto.ScontrinoDto;
import com.management.progettodigestioneacquisti.exception.UserNotFoundException;
import com.management.progettodigestioneacquisti.mapper.ScontrinoMapper;
import com.management.progettodigestioneacquisti.model.Acquisto;
import com.management.progettodigestioneacquisti.model.Cliente;
import com.management.progettodigestioneacquisti.model.Scontrino;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.repository.ScontrinoRepository;
import com.management.progettodigestioneacquisti.service.ClienteService;
import com.management.progettodigestioneacquisti.service.ScontrinoService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/scontrini")
@RequiredArgsConstructor
public class ScontrinoControllerImpl implements ScontrinoController {
    private final ScontrinoService scontrinoService;
    private final ScontrinoMapper scontrinoMapper;
    private final ClienteService clienteService;
    private final AcquistoRepository acquistoRepository;
    private final ScontrinoRepository scontrinoRepository;

    @Override
    @PostMapping("/scontrino/{idCliente}")
    public Scontrino creaScontrino(@PathVariable Long idCliente) throws UserNotFoundException {
        Cliente cliente = clienteService.getClienteById(idCliente);
        List<Acquisto> acquisti = acquistoRepository.findByCliente(cliente);
        Scontrino scontrino = scontrinoService.creaScontrino(idCliente);
        return scontrino;
    }
    @Override
    @GetMapping("/{id}")
    public ScontrinoDto getScontrinoById(@PathVariable Long id) {
        Scontrino scontrino = scontrinoService.getScontrinoById(id);
        return scontrinoMapper.asDTO(scontrino);
    }
    @Override
    @GetMapping("/scontrini/excel")
    public void exportScontriniToExcel(HttpServletResponse response) throws IOException {
        List<Scontrino> scontrini = scontrinoRepository.findAll();
        Workbook scontrinoWorkBook = new XSSFWorkbook();
        Sheet sheet = scontrinoWorkBook.createSheet("Scontrini");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Creazione dell'intestazione del foglio di lavoro
        Row headerRowSc = sheet.createRow(0);
        headerRowSc.createCell(0).setCellValue("Cliente ID");
        headerRowSc.createCell(1).setCellValue("Prodotto acquistato e quantità");
        headerRowSc.createCell(2).setCellValue("Data di acquisto");
        headerRowSc.createCell(3).setCellValue("Prezzo di acquisto");

        // Riempimento del foglio di lavoro con i dati degli acquisti
        int rowNum = 1;
        for (Scontrino scontrino : scontrini) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(scontrino.getCliente().getId());
            row.createCell(1).setCellValue(scontrino.getNomeProdottoAcquistato());
            row.createCell(2).setCellValue(scontrino.getDataScontrino().format(formatter));
            row.createCell(3).setCellValue(scontrino.getTotale().doubleValue());

        }

        // Impostazione della larghezza delle colonne
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 20000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);

        // Impostazione del tipo di contenuto e del nome del file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=scontrini.xlsx");

        // Scrittura del workbook di Excel sulla risposta HTTP
        OutputStream outputStream = response.getOutputStream();
        scontrinoWorkBook.write(outputStream);
        scontrinoWorkBook.close();
        outputStream.close();
    }

}