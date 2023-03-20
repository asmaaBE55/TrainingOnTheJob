package com.management.progettodigestioneacquisti.service;

import com.management.progettodigestioneacquisti.batchcsvtodatabase.ScheduledTasks;
import com.management.progettodigestioneacquisti.exception.InsufficientFundsException;
import com.management.progettodigestioneacquisti.exception.NotEnoughPointsException;
import com.management.progettodigestioneacquisti.exception.ProductNotFoundException;
import com.management.progettodigestioneacquisti.model.*;
import com.management.progettodigestioneacquisti.repository.AcquistoRepository;
import com.management.progettodigestioneacquisti.repository.FidelityCardRepository;
import com.management.progettodigestioneacquisti.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Transactional
@Service
@CacheConfig(cacheNames = "acquisti")
public class AcquistoService {
    private final AcquistoRepository acquistoRepository;
    private final StoricoAcquistiService storicoAcquistiService;
    private final ClienteService clienteService;
    private final FidelityCardService fidelityCardService;
    private final FidelityCardRepository fidelityCardRepository;
    private final ProdottoService prodottoService;
    private final ProdottoRepository prodottoRepository;
    private final ScheduledTasks scheduledTasks;

    public Acquisto compraProdotto(Cliente cliente, Prodotto prodotto, int quantitaDesiderata, BindingResult result) throws ProductNotFoundException, InsufficientFundsException {
        if (result.hasErrors()) {
            throw new ValidationException("Dati non validi" + result);
        }
        BigDecimal prezzoTotale = prodotto.getPrezzoUnitario().multiply(new BigDecimal(quantitaDesiderata));

        if (prezzoTotale.compareTo(cliente.getBudget()) > 0) {
            throw new InsufficientFundsException("Budget insufficiente");
        }

        if (quantitaDesiderata > prodotto.getQuantitaFornitaDallAzienda()) {
            throw new ProductNotFoundException("Quantità esaurita");
        }
        Acquisto acquisto = new Acquisto();
        acquisto.setCliente(cliente);
        acquisto.setQuantitaAcquistata(quantitaDesiderata);
        acquisto.setNomeProdottoAcquistato(prodotto.getNome());
        acquisto.setPrezzoDiAcquisto(prezzoTotale);
        acquisto.setDataAcquisto(LocalDate.now());
        acquisto.setScontrino(acquisto.getScontrino());

        // Cerca il prodotto nello storico degli acquisti del cliente
        StoricoAcquisti storicoAcquisti = cliente.getStoricoAcquisti().stream()
                .filter(sa -> sa.getProdotto().equals(prodotto))
                .findFirst()
                .orElse(null);

        // Aggiorna la quantità acquistata del prodotto nello storico, o crea una nuova riga nello storico se non esiste ancora
        if (storicoAcquisti != null) {
            storicoAcquisti.setQuantitaAcquistata(storicoAcquisti.getQuantitaAcquistata() + quantitaDesiderata);
        } else {
            storicoAcquisti = new StoricoAcquisti();
            storicoAcquisti.setProdotto(prodotto);
            storicoAcquisti.setCliente(cliente);
            storicoAcquisti.setNomeProdotto(prodotto.getNome());
            storicoAcquisti.setDataAcquisto(LocalDate.now());
            storicoAcquisti.setQuantitaAcquistata(quantitaDesiderata);
        }

        acquistoRepository.save(acquisto);


        prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
        if (prodotto.getQuantitaDisponibile() == 0) {
            scheduledTasks.importaQuantitaFornitaDalCsv();
        }
        clienteService.updateNumeroAcquisti(cliente, acquisto);
        clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
        clienteService.updateImportoTotaleSpeso(cliente, prezzoTotale);
        clienteService.aggiornaBudget(cliente, prezzoTotale);

        // Aggiorna l'entità StoricoAcquisti con la quantità acquistata aggiornata
        storicoAcquistiService.saveAcquisto(storicoAcquisti);
        return acquisto;

    }

    public Acquisto compraProdottoConFidelityCard(Cliente cliente, Prodotto prodotto, int quantitaDesiderata, BindingResult result) throws ProductNotFoundException, InsufficientFundsException, NotEnoughPointsException {
        if (result.hasErrors()) {
            throw new ValidationException("Dati non validi" + result);
        }
        BigDecimal prezzoTotaleNormale = prodotto.getPrezzoUnitario().multiply(new BigDecimal(quantitaDesiderata));

        if (prezzoTotaleNormale.compareTo(cliente.getBudget()) > 0) {
            throw new InsufficientFundsException("Budget insufficiente");
        }

        if (quantitaDesiderata > prodotto.getQuantitaFornitaDallAzienda()) {
            throw new ProductNotFoundException("Quantità esaurita");
        }

        BigDecimal prezzoTotale;
        if (prodotto.isStatoSconto()) {
            prezzoTotale = prodotto.getPrezzoScontato().multiply(new BigDecimal(quantitaDesiderata));
        } else {
            prezzoTotale = prezzoTotaleNormale;
        }

        Acquisto acquisto = new Acquisto();
        acquisto.setCliente(cliente);
        acquisto.setQuantitaAcquistata(quantitaDesiderata);
        acquisto.setNomeProdottoAcquistato(prodotto.getNome());
        acquisto.setPrezzoDiAcquisto(prezzoTotale);
        acquisto.setDataAcquisto(LocalDate.now());
        acquisto.setScontrino(acquisto.getScontrino());

        prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
        if (prodotto.getQuantitaDisponibile() == 0) {
            scheduledTasks.importaQuantitaFornitaDalCsv();
        }
        clienteService.updateNumeroAcquisti(cliente, acquisto);
        clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
        clienteService.updateImportoTotaleSpeso(cliente, prezzoTotale);
        clienteService.aggiornaBudget(cliente, prezzoTotale);
        FidelityCard fidelityCard = fidelityCardRepository.findByClienteId(cliente.getId());

        if (fidelityCard != null) {
            // Aggiorna punti accumulati
            fidelityCardService.aggiornaPunti(fidelityCard, prezzoTotale);
        }
        // Cerca il prodotto nello storico degli acquisti del cliente
        StoricoAcquisti storicoAcquisti = cliente.getStoricoAcquisti().stream()
                .filter(sa -> sa.getProdotto().equals(prodotto))
                .findFirst()
                .orElse(null);

        // Aggiorna la quantità acquistata del prodotto nello storico, o crea una nuova riga nello storico se non esiste ancora
        if (storicoAcquisti != null) {
            storicoAcquisti.setQuantitaAcquistata(storicoAcquisti.getQuantitaAcquistata() + quantitaDesiderata);
        } else {
            storicoAcquisti = new StoricoAcquisti();
            storicoAcquisti.setProdotto(prodotto);
            storicoAcquisti.setCliente(cliente);
            storicoAcquisti.setNomeProdotto(prodotto.getNome());
            storicoAcquisti.setDataAcquisto(LocalDate.now());
            storicoAcquisti.setQuantitaAcquistata(quantitaDesiderata);
        }

        // Aggiorna l'entità StoricoAcquisti con la quantità acquistata aggiornata
        storicoAcquistiService.saveAcquisto(storicoAcquisti);
        acquistoRepository.save(acquisto);

        return acquisto;
    }

    public Acquisto prodottoRegalo100PuntiAccumulati(Cliente cliente) throws NotEnoughPointsException, ProductNotFoundException, InsufficientFundsException {
        FidelityCard fidelityCard = fidelityCardService.getFidelityCardByClienteId(cliente.getId());
        if (fidelityCard != null && fidelityCard.getPuntiAccumulati() >= 100) {
            // Cerca un prodotto scontato
            Prodotto prodotto = prodottoRepository.findByStatoSconto(true).stream().findFirst().orElse(null);
            if (prodotto != null) {
                // Acquista il prodotto con prezzo a zero
                Acquisto acquisto = new Acquisto();
                acquisto.setCliente(cliente);
                acquisto.setQuantitaAcquistata(1);
                acquisto.setNomeProdottoAcquistato(prodotto.getNome());
                acquisto.setPrezzoDiAcquisto(BigDecimal.ZERO);
                acquisto.setDataAcquisto(LocalDate.now());
                acquisto.setScontrino(acquisto.getScontrino());
                prodottoService.updateQuantityDopoAcquisto(prodotto, acquisto);
                clienteService.updateNumeroAcquisti(cliente, acquisto);
                clienteService.updateClientStatus(cliente, cliente.getImportoTotaleSpeso());
                clienteService.updateImportoTotaleSpeso(cliente, acquisto.getPrezzoDiAcquisto());
                clienteService.aggiornaBudget(cliente, acquisto.getPrezzoDiAcquisto());
                fidelityCard.setPuntiAccumulati(fidelityCard.getPuntiAccumulati() - 100);
                acquistoRepository.save(acquisto);
                return acquisto;
            }
        }
        throw new NotEnoughPointsException("Non hai abbastanza punti accumulati per avere il prodotto gratuito");
    }

    public void generaReportProfitto() {
        try {
            List<Prodotto> prodotti = prodottoRepository.findAll();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Report Profitto");

            // Intestazioni delle colonne
            String[] headers = {"EAN", "Prezzo di acquisto", "Prezzo Fornitore", "Profitto"};
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Aggiungi i dati delle righe al foglio excel
            int rowNum = 1;
            for (Prodotto prodotto : prodotti) {
                if (prodotto.getQuantitaDisponibile() == 0) {
                    continue;
                }
                if (prodotto.isStatoSconto()) {
                    XSSFRow row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(prodotto.getEanProdotto());
                    row.createCell(1).setCellValue(prodotto.getPrezzoScontato().doubleValue());
                    row.createCell(2).setCellValue(prodotto.getPrezzoFornitore().doubleValue());
                    double profitto = (prodotto.getPrezzoScontato().doubleValue() - prodotto.getPrezzoFornitore().doubleValue()) * (prodotto.getQuantitaAcquistata());
                    row.createCell(3).setCellValue(profitto);
                } else {
                    XSSFRow row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(prodotto.getEanProdotto());
                    row.createCell(1).setCellValue(prodotto.getPrezzoUnitario().doubleValue());
                    row.createCell(2).setCellValue(prodotto.getPrezzoFornitore().doubleValue());
                    double profitto = (prodotto.getPrezzoUnitario().doubleValue() - prodotto.getPrezzoFornitore().doubleValue()) * (prodotto.getQuantitaAcquistata());
                    row.createCell(3).setCellValue(profitto);
                }
            }

            // Calcola la somma dei profitti sia per prodotti scontati sia per quelli non scontati
            double totaleProfitto = prodotti.stream()
                    .filter(p -> p.getQuantitaDisponibile() != 0)
                    .mapToDouble(p -> {
                        if (p.isStatoSconto()) {
                            return (p.getPrezzoScontato().doubleValue() - p.getPrezzoFornitore().doubleValue()) * (p.getQuantitaAcquistata());
                        } else {
                            return (p.getPrezzoUnitario().doubleValue() - p.getPrezzoFornitore().doubleValue()) * (p.getQuantitaAcquistata());
                        }
                    })
                    .sum();


            // Aggiungi il totale del profitto alla fine del foglio excel
            XSSFRow totaleRow = sheet.createRow(rowNum++);
            totaleRow.createCell(4).setCellValue("Totale Profitto:");
            totaleRow.createCell(5).setCellValue(totaleProfitto);

            // Scrivi il file excel sul disco
            String fileName = "report_profitto.xlsx";
            File file = new File(fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            System.out.println("Il report è stato generato correttamente e salvato in: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore durante la generazione del report profitto.");
        }
    }
}
